package ch.szclsb.orbis.plugins;

import java.io.BufferedWriter;
import java.io.IOException;

@FunctionalInterface
public interface IOutputWriter {
    void write(BufferedWriter writer) throws IOException;
}
