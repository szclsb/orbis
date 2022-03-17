package ch.szclsb.orbis.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public abstract class MathPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.task("math")
                .doFirst(task -> System.out.println("Hello from math"));
    }
}

