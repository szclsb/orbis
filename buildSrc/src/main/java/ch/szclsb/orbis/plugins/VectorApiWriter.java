package ch.szclsb.orbis.plugins;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

public class VectorApiWriter extends ApiWriter {
    public VectorApiWriter(Path dir, String generatedPackage,
                           String apiPackage, String apiInterfaceName,
                           String abstractMatPackage, String abstractMatClassName) {
        super(dir, generatedPackage,
                apiPackage, apiInterfaceName,
                abstractMatPackage, abstractMatClassName);
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
                                import jdk.incubator.vector.VectorOperators;
                                import jdk.incubator.vector.VectorSpecies;
                                
                                public class %4$s implements %3$s<%5$s> {
                                    private final VectorSpecies<Float> species = FloatVector.SPECIES_128;
                                
                                """, generatedPackage, apiPackage, apiInterfaceName, apiClassName, matClassName));


            writeVectorMethod(writer, matClassName, "add", "add", '+', th, lanes);
            writeScalarMethod(writer, matClassName, "add", "add", '+', th, lanes);
            writeVectorMethod(writer, matClassName, "sub", "sub", '-', th, lanes);
            writeScalarMethod(writer, matClassName, "sub", "sub", '-', th, lanes);
            writeVectorMethod(writer, matClassName, "mul", "mul", '*', th, lanes);
            writeScalarMethod(writer, matClassName, "mul", "mul", '*', th, lanes);

            writeDot(writer, matClassName, th, lanes);
            writeCross(writer, matClassName, th, lanes);
            writeEquals(writer, matClassName, th, lanes);

            writer.write("""
                                }
                                """);
        });
    }

    private void writeDot(BufferedWriter writer, String type, int th, int lanes) throws IOException {
        writer.write(String.format("""
                    @Override
                    public float dot(%1$s a, %1$s b) {
                        var result = 0f;
                """, type));
        var i = 0;
        for (; i < th; i += 4) {
            writer.write(String.format("""
                            result += FloatVector.fromArray(species, a.data, %1$d)
                                    .mul(FloatVector.fromArray(species, b.data, %1$d))
                                    .reduceLanes(VectorOperators.ADD);
                    """, i));
        }
        for (; i < lanes; i += 1) {
            writer.write(String.format("""
                            result += a.data[%1$d] * b.data[%1$d];
                    """, i));
        }
        writer.write("""
                        return result;
                    }
                """);
    }

    private void writeCross(Writer writer, String type, int th, int lanes) throws IOException {
        writer.write(String.format("""
                    @Override
                    public void cross(%1$s a, %1$s b, %1$s r) {
                    
                    }
                """, type));
//        var i = 0;
//        for (; i < th; i += 4) {
//            writer.write(String.format("""
//                            result += FloatVector.fromArray(species, a.data, %1$d)
//                                    .mul(FloatVector.fromArray(species, b.data, %1$d))
//                                    .reduceLanes(VectorOperators.ADD);
//                    """, i));
//        }
//        for (; i < lanes; i += 1) {
//            writer.write(String.format("""
//                            result += a.data[%1$d] * b.data[%1$d];
//                    """, i));
//        }
//        writer.write("""
//                        return result;
//                    }
//                """);
    }
}
