package ch.szclsb.orbis.plugins;

import org.gradle.api.NamedDomainObjectContainer;

public interface MathPluginExtension {
    NamedDomainObjectContainer<MatrixDef> getMatrices();
}

