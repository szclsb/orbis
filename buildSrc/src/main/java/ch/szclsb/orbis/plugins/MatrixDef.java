package ch.szclsb.orbis.plugins;

import org.gradle.api.provider.Property;

public interface MatrixDef {
   String getName();

   Property<String> getType();

   Property<Integer> getRows();

   Property<Integer> getColumns();
}
