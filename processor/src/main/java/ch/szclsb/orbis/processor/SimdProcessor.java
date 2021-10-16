package ch.szclsb.orbis.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("ch.szclsb.orbis.processor.Simd")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SimdProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "STARTED PROCESSOR");

        for (TypeElement annotation : annotations) {
            roundEnv.getElementsAnnotatedWith(annotation).forEach(element -> {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating Simd optimized API for class: " + element.toString());
                try {
                    createAPI(element.toString());
                } catch (Exception e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                }
            });
        }
        return false;
    }

    private void createAPI(String classname) throws IOException {
        var pos = classname.lastIndexOf('.');
        var simpleVectorName = classname.substring(pos + 1);
        var simpleApiName = simpleVectorName + "API";
        var packageName = classname.substring(0, pos);
        var file = processingEnv.getFiler().createSourceFile(packageName + "." + simpleApiName);
        try (var writer = file.openWriter()) {
            writer.write(String.format("""
                    package %1$s;
                    
                    import %1$s.%4$s;

                    public class %2$s implements %4$s<%3$s> {
                        @Override
                        public boolean equals(%3$s a, %3$s b) {
                            return false;
                        }
                    
                        @Override
                        public void add(%3$s a, %3$s b, %3$s r) {
                            
                        }
                        
                        @Override
                        public void add(%3$s a, float s, %3$s r) {
                            
                        }
                        
                        @Override
                        public void sub(%3$s a, %3$s b, %3$s r) {
                            
                        }
                        
                        @Override
                        public void sub(%3$s a, float s, %3$s r) {
                            
                        }
                        
                        @Override
                        public void mul(%3$s a, %3$s b, %3$s r) {
                            
                        }
                        
                        @Override
                        public void mul(%3$s a, float s, %3$s r) {
                            
                        }
                        
                        @Override
                        public float dot(%3$s a, %3$s b) {
                            return 0f;
                        }
                    }
                    """, packageName,
                    simpleApiName,
                    simpleVectorName,
                    "IVectorApi"
            ));
        }
    }
}
