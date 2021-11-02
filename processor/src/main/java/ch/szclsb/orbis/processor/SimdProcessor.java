package ch.szclsb.orbis.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({
        SimdProcessor.vectorAnnotationName,
        SimdProcessor.matrixAnnotationName
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SimdProcessor extends AbstractProcessor {
    public static final String vectorAnnotationName = "ch.szclsb.orbis.processor.SimdVec";
    public static final String matrixAnnotationName = "ch.szclsb.orbis.processor.SimdMatrix";

    // used internal vector definition
    private static final String SPECIES_ELEMENT = "FloatVector.SPECIES_128";
    private static final int SPECIES_LANES = 4;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            if (annotation.getQualifiedName().contentEquals(vectorAnnotationName)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating Simd optimized vector API");
                try {
                    createVectorApi(roundEnv.getElementsAnnotatedWith(annotation),
                            "ch.szclsb.orbis.math", "SimdVectorApi", "IFVectorApi");
                } catch (Exception e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                }
            } else if (annotation.getQualifiedName().contentEquals(matrixAnnotationName)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating Simd optimized matrix API");
                try {
                    createMatrixApi(roundEnv.getElementsAnnotatedWith(annotation),
                            "ch.szclsb.orbis.math", "SimdMatrixApi", "IFMatrixApi");
                } catch (Exception e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                }
            }
        }

        return false;
    }

    private void createVectorApi(Set<? extends Element> classes, String packageName, String apiName, String interfaceName) throws IOException {
        var file = processingEnv.getFiler().createSourceFile(packageName + "." + apiName);
        try (var writer = file.openWriter()) {
            writer.write(String.format("""
                            package %1$s;
                                                
                            import %1$s.%3$s;
                            import jdk.incubator.vector.FloatVector;
                            import jdk.incubator.vector.VectorOperators;
                            import jdk.incubator.vector.VectorSpecies;

                            public class %2$s implements \
                            """,
                    packageName,
                    apiName,
                    interfaceName
            ));
            writer.write(classes.stream()
                    .map(element -> String.format("%1$s<%2$s> ", interfaceName, element.toString()))
                    .collect(Collectors.joining(", ")));
            writer.write(String.format("""
                    {
                        private final VectorSpecies<Float> species = %s;
                        
                    """, SPECIES_ELEMENT
            ));
            for (var element : classes) {
                var type = element.toString();
                var annotation = element.getAnnotation(SimdVec.class);
                var lanes = annotation.lanes();
                var th = (lanes / SPECIES_LANES) * SPECIES_LANES;

                writeVectorMethod(writer, type, "add","add", '+', th, lanes);
                writeScalarMethod(writer, type, "add","add", '+', th, lanes);
                writeVectorMethod(writer, type, "sub","sub", '-', th, lanes);
                writeScalarMethod(writer, type, "sub","sub", '-', th, lanes);
                writeVectorMethod(writer, type, "mul","mul", '*', th, lanes);
                writeScalarMethod(writer, type, "mul","mul", '*', th, lanes);

                writeDot(writer, type, th, lanes);
                writeCross(writer, type, th, lanes);
                writeEquals(writer, type, th, lanes);
            }

            writer.write("""
                    }
                    """);
        }
    }

    private void createMatrixApi(Set<? extends Element> classes, String packageName, String apiName, String interfaceName) throws IOException {
        var matrixMap = new TreeMap<Integer, TreeMap<Integer, Element>>();
        for (var element : classes) {
            var annotation = element.getAnnotation(SimdMatrix.class);
            matrixMap.putIfAbsent(annotation.rows(), new TreeMap<>());
            matrixMap.get(annotation.rows()).put(annotation.columns(), element);
        }

        var file = processingEnv.getFiler().createSourceFile(packageName + "." + apiName);
        try (var writer = file.openWriter()) {
            writer.write(String.format("""
                            package %1$s;
                                                
                            import %1$s.%3$s;
                            import jdk.incubator.vector.FloatVector;
                            import jdk.incubator.vector.VectorOperators;
                            import jdk.incubator.vector.VectorSpecies;

                            public class %2$s implements \
                            """,
                    packageName,
                    apiName,
                    interfaceName
            ));
            writer.write(classes.stream()
                    .map(element -> String.format("%1$s<%2$s> ", interfaceName, element.toString()))
                    .collect(Collectors.joining(", ")));
            writer.write(String.format("""
                    {
                        private final VectorSpecies<Float> species = %s;
                        
                    """, SPECIES_ELEMENT
            ));
            for (var element : classes) {
                var type = element.toString();
                var annotation = element.getAnnotation(SimdMatrix.class);
                var lanes = annotation.rows() * annotation.columns();
                var th = (lanes / SPECIES_LANES) * SPECIES_LANES;

                writeVectorMethod(writer, type, "add", "add",'+', th, lanes);
                writeScalarMethod(writer, type, "add", "add",'+', th, lanes);
                writeVectorMethod(writer, type, "sub", "sub",'-', th, lanes);
                writeScalarMethod(writer, type, "sub", "sub",'-', th, lanes);
                writeVectorMethod(writer, type, "_mul","mul", '*', th, lanes);
                writeScalarMethod(writer, type, "mul", "mul",'*', th, lanes);

                // matrix multiplications
                for (var element1 : classes) {
                    var otherAnnotation = element.getAnnotation(SimdMatrix.class);
                    if (annotation.columns() == otherAnnotation.rows()) {
                        Element result = null;
                        var resultMap = matrixMap.get(annotation.rows());
                        if (resultMap != null) {
                            result = resultMap.get(otherAnnotation.columns());
                        }
                        if (result != null) {
                            var otherType = element1.toString();
                            var resultType = result.toString();
                            writeMatrixMul(writer, type, otherType, resultType, annotation.rows(),
                                    annotation.columns(), otherAnnotation.rows(), otherAnnotation.columns());
                        }
                    }
                }

                writeEquals(writer, type, th, lanes);
            }

            writer.write("""
                    }
                    """);
        }
    }

    private void writeVectorMethod(Writer writer, String type, String name, String internalName, char operator, int th, int lanes) throws IOException {
        writer.write(String.format("""
                    @Override
                    public void %1$s(%2$s a, %2$s b, %2$s r) {
                """, name, type));
        var i = 0;
        for (; i < th; i += 4) {
            writer.write(String.format("""
                            FloatVector.fromArray(species, a.data, %1$d)
                                    .%2$s(FloatVector.fromArray(species, b.data, %1$d))
                                    .intoArray(r.data, %1$d);
                    """, i, internalName));
        }
        for (; i < lanes; i += 1) {
            writer.write(String.format("""
                            r.data[%1$d] = a.data[%1$d] %2$c b.data[%1$d];
                    """, i, operator));
        }
        writer.write("    }\n");
    }

    private void writeScalarMethod(Writer writer, String type, String name, String internalName, char operator, int th, int lanes) throws IOException {
        writer.write(String.format("""
                    @Override
                    public void %1$s(%2$s a, float s, %2$s r) {
                """, name, type));
        var i = 0;
        for (; i < th; i += 4) {
            writer.write(String.format("""
                            FloatVector.fromArray(species, a.data, %1$d)
                                    .%2$s(s)
                                    .intoArray(r.data, %1$d);
                    """, i, internalName));
        }
        for (; i < lanes; i += 1) {
            writer.write(String.format("""
                            r.data[%1$d] = a.data[%1$d] %2$c s;
                    """, i, operator));
        }
        writer.write("    }\n");
    }

    private void writeDot(Writer writer, String type, int th, int lanes) throws IOException {
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
        // todo implement
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

    private void writeEquals(Writer writer, String type, int th, int lanes) throws IOException {
        var statements = new ArrayList<String>();
        var i = 0;
        for (; i < th; i += 4) {
            statements.add(String.format("""
                    FloatVector.fromArray(species, a.data, %1$d)
                                .sub(FloatVector.fromArray(species, b.data, %1$d))
                                .abs().lt(MathUtils.TOLERANCE).allTrue()\
                    """, i));
        }
        for (; i < lanes; i += 1) {
            statements.add(String.format("""
                    MathUtils.isFloatEquals(a.data[%1$d], b.data[%1$d])\
                    """, i));
        }
        writer.write(String.format("""
                    @Override
                    public boolean equals(%1$s a, %1$s b) {
                        return %2$s;
                    }
                """, type, String.join("""
                            
                            && \
                """, statements)));
    }

    private void writeMatrixMul(Writer writer, String type, String otherType, String resultType,
                                int leftRows, int leftColumns, int rightRows, int rightColumns) throws IOException {
        // todo implement
        writer.write(String.format("""
                    @Override
                    public void mul(%1$s a, %2$s b, %3$s r) {
                        
                    }
                """, type, otherType, resultType));
    }
}
