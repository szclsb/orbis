package ch.szclsb.orbis.plugins;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

public interface MathPluginExtension {
    DirectoryProperty getOutputDir();

    Property<String> getPackageName();

    NamedDomainObjectContainer<MatrixDef> getMatrices();
}

