package ch.szclsb.orbis.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({
        "ch.szclsb.orbis.processor.SimdVec",
        "ch.szclsb.orbis.processor.SimdMatrix"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SimdProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "STARTED PROCESSOR");

        for (TypeElement annotation : annotations) {
            roundEnv.getElementsAnnotatedWith(annotation).forEach(element -> {
                var vecAnnotation = element.getAnnotation(SimdVec.class);
                if (vecAnnotation != null) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating Simd optimized API for class: " + element);
                    try {
                        createAPI(element.toString(), vecAnnotation.lanes());
                    } catch (Exception e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    }
                }
                var matAnnotation = element.getAnnotation(SimdMatrix.class);
                if (matAnnotation != null) {

                }
            });
        }
        return false;
    }

    private void createAPI(String classname, int lanes) throws IOException {
        var pos = classname.lastIndexOf('.');
        var simpleVectorName = classname.substring(pos + 1);
        var simpleApiName = simpleVectorName + "API";
        var packageName = classname.substring(0, pos);
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
            writeVectorMethod(writer, simpleVectorName, "add", '+', th, lanes);
            writeScalarMethod(writer, simpleVectorName, "add", '+', th, lanes);
            writeVectorMethod(writer, simpleVectorName, "sub", '-', th, lanes);
            writeScalarMethod(writer, simpleVectorName, "sub", '-', th, lanes);
            writeVectorMethod(writer, simpleVectorName, "mul", '*', th, lanes);
            writeScalarMethod(writer, simpleVectorName, "mul", '*', th, lanes);

            writeDot(writer, simpleVectorName, th, lanes);
            writeCross(writer, simpleVectorName, th, lanes);
            writeEquals(writer, simpleVectorName, th, lanes);

            writer.write("""
                    }
                    """);
        }
    }

    private void writeVectorMethod(Writer writer, String type, String name, char operator, int th, int lanes) throws IOException {
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
                    """, i, name));
        }
        for (; i < lanes; i += 1) {
            writer.write(String.format("""
                            r.data[%1$d] = a.data[%1$d] %2$c b.data[%1$d];
                    """, i, operator));
        }
        writer.write("    }\n");
    }

    private void writeScalarMethod(Writer writer, String type, String name, char operator, int th, int lanes) throws IOException {
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
                    """, i, name));
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
