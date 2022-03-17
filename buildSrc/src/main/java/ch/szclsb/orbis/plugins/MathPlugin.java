package ch.szclsb.orbis.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public abstract class MathPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        var extension = project.getExtensions().create("math", MathPluginExtension.class);

        project.task("math").doFirst(task -> {
            for (var msg : extension.getMatrices()) {
                System.out.println(msg.getName());
            }
        });
    }
}

