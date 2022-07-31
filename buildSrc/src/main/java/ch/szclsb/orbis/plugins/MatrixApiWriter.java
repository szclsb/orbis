package ch.szclsb.orbis.plugins;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class MatrixApiWriter extends ApiWriter {
    private final Set<MatrixDef> matrices;
    private final Map<Integer, String> masks;

    public MatrixApiWriter(Path dir, String generatedPackage,
                           String apiPackage, String apiInterfaceName,
                           String abstractMatPackage, String abstractMatClassName,
                           Collection<MatrixDef> matrices) {
        super(dir, generatedPackage,
                apiPackage, apiInterfaceName,
                abstractMatPackage, abstractMatClassName);
        Comparator<MatrixDef> rowComparator = Comparator.comparing(m -> m.getRows().get());
        Comparator<MatrixDef> columnComparator = Comparator.comparing(m -> m.getColumns().get());
        this.matrices = new TreeSet<>(rowComparator.thenComparing(columnComparator));
        this.matrices.addAll(matrices);
        this.masks = new TreeMap<>();
        this.masks.put(1, "mask1");
        this.masks.put(2, "mask2");
        this.masks.put(3, "mask3");
    }

    @Override
    public void write(String matClassName, String apiClassName,
                      int rows, int columns) throws IOException {
        var lanes = rows * columns;
        var th = (lanes / 4) * 4;
        writeFile(apiClassName, writer -> {
            writer.write(String.format("""
                    // GENERATED CLASS, DO NOT MODIFY THIS CLASS: CHANGES WILL BE OVERWRITTEN
                    package %1$s;
                                                    
                    import %2$s.%3$s;
                    import ch.szclsb.orbis.math.MathUtils;
                                                    
                    import jdk.incubator.vector.FloatVector;
                    import jdk.incubator.vector.VectorMask;
                    import jdk.incubator.vector.VectorOperators;
                    import jdk.incubator.vector.VectorSpecies;
                                                    
                    public class %4$s implements %3$s<%5$s> {
                        private final VectorSpecies<Float> species = FloatVector.SPECIES_128;
                        private final VectorMask<Float> mask1 = VectorMask.fromArray(species, new boolean[]{true, false, false, false}, 0);
                        private final VectorMask<Float> mask2 = VectorMask.fromArray(species, new boolean[]{true, true, false, false}, 0);
                        private final VectorMask<Float> mask3 = VectorMask.fromArray(species, new boolean[]{true, true, true, false}, 0);
                                                    
                    """, generatedPackage, apiPackage, apiInterfaceName, apiClassName, matClassName));


            writeVectorMethod(writer, matClassName, "add", "add", '+', th, lanes);
            writeScalarMethod(writer, matClassName, "add", "add", '+', th, lanes);
            writeVectorMethod(writer, matClassName, "sub", "sub", '-', th, lanes);
            writeScalarMethod(writer, matClassName, "sub", "sub", '-', th, lanes);
            writeVectorMethod(writer, matClassName, "_mul", "mul", '*', th, lanes);
            writeScalarMethod(writer, matClassName, "mul", "mul", '*', th, lanes);



            var it1 = matrices.stream()
                    .filter(m -> m.getRows().get().equals(columns))
                    .iterator();
            while (it1.hasNext()) {
                var otherMatrix = it1.next();
                var otherClassType = otherMatrix.getName();
                var resultColumn = otherMatrix.getColumns().get();
                var or = matrices.stream()
                        .filter(r -> r.getRows().get().equals(rows) && r.getColumns().get().equals(resultColumn))
                        .findAny();
                if (or.isPresent()) {
                    var resultMatrix = or.get();
                    var resultClassType = resultMatrix.getName();
                    var cth = (columns / 4) * 4;  // column threshold
                    writeMatrixMultiplyMethod(writer, matClassName, otherClassType, resultClassType,
                            rows, resultColumn, columns, cth);
                }
            }

            writer.write(String.format("""
                        @Override
                        public void mul(%1$s a, %2$s b, %2$s r) {
                            throw new UnsupportedOperationException();
                        }
                    """, matClassName, "FMatrix"));

            writeEquals(writer, matClassName, th, lanes);

            writer.write("""
                    }
                    """);
        });
    }

    private void writeMatrixMultiplyMethod(BufferedWriter writer, String type, String otherType, String resultType,
                                           int rows, int columns, int depth, int cth) throws IOException {
        writer.write(String.format("""
                        public void mul(%1$s a, %2$s b, %3$s r) {
                    """, type, otherType, resultType));

        var i = 0;
        for(; i < cth; i += 4) {
            // cache b vectors
            for (var d = 0; d < depth; d++) {
                writer.write(String.format("""
                                var B%1$dx%2$d = FloatVector.fromArray(species, b.data, %3$d);
                        """, d, i, d * columns + i));
            }
            writer.write("""
                    
                    """);
            // calculate result matrix
            for (var r = 0; r < rows; r++) {
                writer.write(String.format("""
                                B%1$dx%2$d.mul(a.data[%3$d])
                        """, 0, i, r * depth));
                for (var d = 1; d < depth; d++) {
                    writer.write(String.format("""
                                        .add(B%1$dx%2$d.mul(a.data[%3$d]))
                            """, d, i, r * depth + d));
                }
                writer.write(String.format("""
                                    .intoArray(r.data, %d);
                        """, r * columns + i));
            }
            writer.write("""
                    
                    """);
        }

        var mask = this.masks.get(columns - cth);
        if (mask != null) {
            // cache b vectors
            for (var d = 0; d < depth; d++) {
                writer.write(String.format("""
                                var B%1$dx%2$d = FloatVector.fromArray(species, b.data, %3$d, %4$s);
                        """, d, i, d * columns + i, mask));
            }
            writer.write("""
                    
                    """);
            // calculate result matrix
            for (var r = 0; r < rows; r++) {
                writer.write(String.format("""
                                B%1$dx%2$d.mul(a.data[%3$d])
                        """, 0, i, r * depth));
                for (var d = 1; d < depth; d++) {
                    writer.write(String.format("""
                                        .add(B%1$dx%2$d.mul(a.data[%3$d]))
                            """, d, i, r * depth + d));
                }
                writer.write(String.format("""
                                    .intoArray(r.data, %1$d, %2$s);
                        """, r * columns + i, mask));
            }
        }

        writer.write("""
                        }
                    """);
    }
}
