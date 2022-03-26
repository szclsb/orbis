package ch.szclsb.orbis.plugins;

import java.io.IOException;
import java.nio.file.Path;

public class MatrixApiWriter extends ApiWriter {
    public MatrixApiWriter(Path dir, String generatedPackage,
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
            writeVectorMethod(writer, matClassName, "_mul", "mul", '*', th, lanes);
            writeScalarMethod(writer, matClassName, "mul", "mul", '*', th, lanes);


            writer.write(String.format("""
                    @Override
                    public <B extends %2$s, R extends %2$s> void mul(%1$s a, B b, R r) {
                        throw new UnsupportedOperationException();
                    }
                """, matClassName, "FMatrix"));

//            writeMatrixMultiplyMethod();

            writeEquals(writer, matClassName, th, lanes);

            writer.write("""
                                }
                                """);
        });
    }
}
