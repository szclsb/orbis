package ch.szclsb.orbis.plugins;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public abstract class ApiWriter {
    private final Path dir;
    protected final String generatedPackage;
    protected final String apiPackage;
    protected final String apiInterfaceName;
    protected final String abstractMatPackage;
    protected final String abstractMatClassName;

    public ApiWriter(Path dir, String generatedPackage,
                     String apiPackage, String apiInterfaceName,
                     String abstractMatPackage, String abstractMatClassName) {
        this.dir = dir;
        this.generatedPackage = generatedPackage;
        this.apiPackage = apiPackage;
        this.apiInterfaceName = apiInterfaceName;
        this.abstractMatPackage = abstractMatPackage;
        this.abstractMatClassName = abstractMatClassName;
    }

    public abstract void write(String matClassName, String apiClassName,
                               int rows, int columns) throws IOException;

    protected void writeFile(String name, IOutputWriter outputWriter) throws IOException {
        var file = dir.resolve(name + ".java");
        var tmpFile = dir.resolve(name + ".tmp");
        try (var writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(tmpFile,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)))) {
            outputWriter.write(writer);
            Files.move(tmpFile, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.out.println("warning: " + e.getMessage());
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    protected void writeVectorMethod(BufferedWriter writer, String type, String name, String internalName, char operator, int th, int lanes) throws IOException {
        writer.write(String.format("""
                    @Override
                    public void %1$s(%2$s a, %2$s b, %2$s r) {
                """, name, type));
        var i = 0;
        for (; i < th; i += 4) {
            writer.write(String.format("""
                            FloatVector.fromArray(species, a.data, %1$d)
                                    .%2$s(FloatVector.fromArray(species, b.data, %1$d))
                                    .intoArray(r.data, %1$d);
                    """, i, internalName));
        }
        for (; i < lanes; i += 1) {
            writer.write(String.format("""
                            r.data[%1$d] = a.data[%1$d] %2$c b.data[%1$d];
                    """, i, operator));
        }
        writer.write("    }\n");
    }

    protected void writeScalarMethod(BufferedWriter writer, String type, String name, String internalName, char operator, int th, int lanes) throws IOException {
        writer.write(String.format("""
                    @Override
                    public void %1$s(%2$s a, float s, %2$s r) {
                """, name, type));
        var i = 0;
        for (; i < th; i += 4) {
            writer.write(String.format("""
                            FloatVector.fromArray(species, a.data, %1$d)
                                    .%2$s(s)
                                    .intoArray(r.data, %1$d);
                    """, i, internalName));
        }
        for (; i < lanes; i += 1) {
            writer.write(String.format("""
                            r.data[%1$d] = a.data[%1$d] %2$c s;
                    """, i, operator));
        }
        writer.write("    }\n");
    }

    protected void writeEquals(Writer writer, String type, int th, int lanes) throws IOException {
        var statements = new ArrayList<String>();
        var i = 0;
        for (; i < th; i += 4) {
            statements.add(String.format("""
                    FloatVector.fromArray(species, a.data, %1$d)
                                .sub(FloatVector.fromArray(species, b.data, %1$d))
                                .abs().lt(MathUtils.TOLERANCE).allTrue()\
                    """, i));
        }
        for (; i < lanes; i += 1) {
            statements.add(String.format("""
                    MathUtils.isFloatEquals(a.data[%1$d], b.data[%1$d])\
                    """, i));
        }
        writer.write(String.format("""
                    @Override
                    public boolean equals(%1$s a, %1$s b) {
                        return %2$s;
                    }
                """, type, String.join("""
                            
                            && \
                """, statements)));
    }
}
