package ch.szclsb.orbis.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

@SupportedAnnotationTypes({
        SimdProcessor.VECTOR_ANNOTATION_CLASS,
        SimdProcessor.MATRIX_ANNOTATION_CLASS
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SimdProcessor extends AbstractProcessor {
    public static final String VECTOR_ANNOTATION_CLASS = "ch.szclsb.orbis.processor.SimdVec";
    public static final String MATRIX_ANNOTATION_CLASS = "ch.szclsb.orbis.processor.SimdMatrix";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "STARTED PROCESSOR");
        for (TypeElement annotation : annotations) {
            if (annotation.getQualifiedName().contentEquals(VECTOR_ANNOTATION_CLASS)) {
                roundEnv.getElementsAnnotatedWith(annotation).forEach(element -> {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating Simd optimized API for class: " + element);
                    try {
                        createVectorAPI(element);
                    } catch (Exception e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    }
                });
            } else if (annotation.getQualifiedName().contentEquals(MATRIX_ANNOTATION_CLASS)) {
                var matrices = roundEnv.getElementsAnnotatedWith(annotation);
                matrices.forEach(element -> {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating Simd optimized API for class: " + element);
                    try {
                        createMatrixAPI(element, matrices);
                    } catch (Exception e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    }
                });
            }
        }
        return false;
    }

    private void createVectorAPI(Element element) throws IOException {
        var classname = element.toString();
        var vecAnnotation = element.getAnnotation(SimdVec.class);
        var pos = classname.lastIndexOf('.');
        var simpleVectorName = classname.substring(pos + 1);
        var simpleApiName = simpleVectorName + "API";
        var packageName = classname.substring(0, pos);
        var lanes = vecAnnotation.lanes();
        var th = (lanes / 4) * 4;
        var file = processingEnv.getFiler().createSourceFile(packageName + "." + simpleApiName);
        try (var writer = file.openWriter()) {
            writer.write(String.format("""
                            package %1$s;
                                                
                            import %1$s.%4$s;
                            import jdk.incubator.vector.FloatVector;
                            import jdk.incubator.vector.VectorOperators;
                            import jdk.incubator.vector.VectorSpecies;

                            public class %2$s implements %4$s<%3$s> {
                                private final VectorSpecies<Float> species = FloatVector.SPECIES_128;
                                
                            """, packageName,
                    simpleApiName,
                    simpleVectorName,
                    "IFVectorApi"
            ));
            writeVectorMethod(writer, simpleVectorName, "add", "add", '+', th, lanes);
            writeScalarMethod(writer, simpleVectorName, "add", "add", '+', th, lanes);
            writeVectorMethod(writer, simpleVectorName, "sub", "sub", '-', th, lanes);
            writeScalarMethod(writer, simpleVectorName, "sub", "sub", '-', th, lanes);
            writeVectorMethod(writer, simpleVectorName, "mul", "mul", '*', th, lanes);
            writeScalarMethod(writer, simpleVectorName, "mul", "mul", '*', th, lanes);

            writeDot(writer, simpleVectorName, th, lanes);
            writeCross(writer, simpleVectorName, th, lanes);
            writeEquals(writer, simpleVectorName, th, lanes);

            writer.write("""
                    }
                    """);
        }
    }

    private void createMatrixAPI(Element element, Set<? extends Element> elements) throws IOException {
        var classname = element.toString();
        var matAnnotation = element.getAnnotation(SimdMatrix.class);
        var pos = classname.lastIndexOf('.');
        var simpleMatrixName = classname.substring(pos + 1);
        var simpleApiName = simpleMatrixName + "API";
        var packageName = classname.substring(0, pos);
        var lanes = matAnnotation.rows() * matAnnotation.columns();
        var th = (lanes / 4) * 4;

        // cache matrix dimensions for multiplications
        var matrixMap = new TreeMap<Integer, TreeMap<Integer, Element>>();
        for (var element1 : elements) {
            var matAnnotation1 = element1.getAnnotation(SimdMatrix.class);
            matrixMap.putIfAbsent(matAnnotation1.rows(), new TreeMap<>());
            matrixMap.get(matAnnotation1.rows()).put(matAnnotation1.columns(), element1);
        }

        var file = processingEnv.getFiler().createSourceFile(packageName + "." + simpleApiName);
        try (var writer = file.openWriter()) {
            writer.write(String.format("""
                            package %1$s;
                                                
                            import %1$s.%4$s;
                            import jdk.incubator.vector.FloatVector;
                            import jdk.incubator.vector.VectorOperators;
                            import jdk.incubator.vector.VectorSpecies;

                            public class %2$s implements %4$s<%3$s> {
                                private final VectorSpecies<Float> species = FloatVector.SPECIES_128;
                                
                            """, packageName,
                    simpleApiName,
                    simpleMatrixName,
                    "IFMatrixApi"
            ));
            writeVectorMethod(writer, simpleMatrixName, "add", "add", '+', th, lanes);
            writeScalarMethod(writer, simpleMatrixName, "add", "add", '+', th, lanes);
            writeVectorMethod(writer, simpleMatrixName, "sub", "sub", '-', th, lanes);
            writeScalarMethod(writer, simpleMatrixName, "sub", "sub", '-', th, lanes);
            writeVectorMethod(writer, simpleMatrixName, "_mul", "mul", '*', th, lanes);
            writeScalarMethod(writer, simpleMatrixName, "mul", "mul", '*', th, lanes);

            // matrix multiplications
            writer.write(String.format("""
                    @Override
                    public void mul(%1$s a, %2$s b, %2$s r) {
                        throw new UnsupportedOperationException();
                    }
                """, simpleMatrixName, "FMatrix"));
            for (var element1 : elements) {
                var otherAnnotation = element.getAnnotation(SimdMatrix.class);
                if (matAnnotation.columns() == otherAnnotation.rows()) {
                    Element result = null;
                    var resultMap = matrixMap.get(matAnnotation.rows());
                    if (resultMap != null) {
                        result = resultMap.get(otherAnnotation.columns());
                    }
                    if (result != null) {
                        var otherType = element1.toString();
                        var resultType = result.toString();
                        writeMatMul(writer, simpleMatrixName, otherType, resultType, matAnnotation.rows(),
                                matAnnotation.columns(), otherAnnotation.rows(), otherAnnotation.columns());
                    }
                }
            }

            writeEquals(writer, simpleMatrixName, th, lanes);
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

    private void writeMatMul(Writer writer, String type, String otherType, String resultType,
                                int leftRows, int leftColumns, int rightRows, int rightColumns) throws IOException {
        // todo implement
        writer.write(String.format("""
                    
                    public void mul(%1$s a, %2$s b, %3$s r) {
                        
                    }
                """, type, otherType, resultType));
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
}
