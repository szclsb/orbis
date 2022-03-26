package ch.szclsb.orbis.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public abstract class MathPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        var extension = project.getExtensions().create("math", MathPluginExtension.class);

        project.task("math").doFirst(task -> {
            try {
                var dir = extension.getOutputDir().get().getAsFile().toPath();
                prepareDir(dir);

                var generatedPackage = extension.getGeneratedPackage().get();
//                var apiClassName = extension.getGeneratedClassName().get();
                var apiPackage = extension.getApiPackage().get();
                var apiInterfaceName = extension.getApiInterfaceName().get();
                var vApiInterfaceName = extension.getVecApiInterfaceName().get();
                var abstractMatPackage = extension.getAbstractMatPackage().get();
                var abstractMatClassName = extension.getAbstractMatClassName().get();

                var vectorClassWriter = new VectorClassWriter(dir, generatedPackage,
                        apiPackage, vApiInterfaceName,
                        abstractMatPackage, abstractMatClassName);
                var matrixClassWriter = new MatrixClassWriter(dir, generatedPackage,
                        apiPackage, apiInterfaceName,
                        abstractMatPackage, abstractMatClassName);

                var vectorApiWriter = new VectorApiWriter(dir, generatedPackage,
                        apiPackage, vApiInterfaceName,
                        abstractMatPackage, abstractMatClassName);
                var matrixApiWriter = new MatrixApiWriter(dir, generatedPackage,
                        apiPackage, apiInterfaceName,
                        abstractMatPackage, abstractMatClassName);

                for (var matrix : extension.getMatrices()) {
                    var matClassName = matrix.getName();
                    var apiClassName = matClassName + "API";
                    var rows = matrix.getRows().get();
                    var columns = matrix.getColumns().get();
                    if (columns == 1 || rows == 1) {
                        vectorClassWriter.write(matClassName, apiClassName, rows, columns);
                        vectorApiWriter.write(matClassName, apiClassName, rows, columns);
                    } else {
                        matrixClassWriter.write(matClassName, apiClassName, rows, columns);
                        matrixApiWriter.write(matClassName, apiClassName, rows, columns);
                    }
                }
            } catch (IOException e) {
                System.err.println("error: " + e.getMessage());
            }
        });
    }

    private void prepareDir(Path dir) throws IOException {
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
    }
}

