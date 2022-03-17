package ch.szclsb.orbis.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

public abstract class MathPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        var extension = project.getExtensions().create("math", MathPluginExtension.class);

        project.task("math").doFirst(task -> {
            try {
                var dir = extension.getOutputDir().get().getAsFile().toPath();
                if (Files.exists(dir)) {
                    Files.walk(dir)
                            .filter(path -> !dir.equals(path))
                            .sorted(Comparator.reverseOrder())
                            .forEach(path -> {
                                try {
                                    Files.delete(path);
                                } catch (IOException e) {
                                    System.err.println("error: " + e.getMessage());
                                }
                            });
                } else {
                    Files.createDirectories(dir);
                }
                for (var matrix : extension.getMatrices()) {
                    var content = String.format("""
                        package %1$s;
                        
                        public class %2$s {
                            private %3$s[] data = new %3$s[%4$d];
                            
                            public %3$s[] get() {
                                return data;
                            }
                        }
                        """, extension.getPackageName().get(), matrix.getName(), matrix.getType().get(), matrix.getRows().get() * matrix.getColumns().get());
                    writeFile(dir, matrix.getName(), content);
                }
            } catch (IOException e) {
                System.err.println("error: " + e.getMessage());
            }
        });
    }

    private void writeFile(Path dir, String name, String content) throws IOException {
        var file = dir.resolve(name + ".java");
        var tmpFile = dir.resolve(name + ".tmp");
        try (var writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(tmpFile,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)))) {
            writer.write(content);
            Files.move(tmpFile, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.out.println("warning: " + e.getMessage());
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }
}

