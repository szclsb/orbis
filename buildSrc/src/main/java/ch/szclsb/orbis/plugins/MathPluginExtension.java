package ch.szclsb.orbis.plugins;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

public interface MathPluginExtension {
    DirectoryProperty getOutputDir();

    Property<String> getApiPackage();

    Property<String> getApiInterfaceName();

    Property<String> getVecApiInterfaceName();

    Property<String> getAbstractMatPackage();

    Property<String> getAbstractMatClassName();

    Property<String> getGeneratedPackage();

    Property<String> getGeneratedClassName();

    NamedDomainObjectContainer<MatrixDef> getMatrices();
}

