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
        var classnameApi = classname + "API";
        var pos = classnameApi.lastIndexOf('.');
        var file = processingEnv.getFiler().createSourceFile(classnameApi);
        try (var writer = file.openWriter()) {
            writer.write(String.format("""
                    package %s;

                    public class %s {
                    
                    }
                    """, classnameApi.substring(0, pos), classnameApi.substring(pos + 1)));
        }
    }
}
