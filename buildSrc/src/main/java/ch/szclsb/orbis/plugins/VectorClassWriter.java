package ch.szclsb.orbis.plugins;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VectorClassWriter extends ApiWriter {
    public VectorClassWriter(Path dir, String generatedPackage,
                             String apiPackage, String apiInterfaceName,
                             String abstractMatPackage, String abstractMatClassName) {
        super(dir, generatedPackage,
                apiPackage, apiInterfaceName,
                abstractMatPackage, abstractMatClassName);
    }

    @Override
    public void write(String matClassName, String apiClassName,
                      int rows, int columns) throws IOException {
        var lanes = rows * columns;
        var args = IntStream.range(0, lanes).mapToObj(i -> "a" + (i + 1)).toList();

        writeFile(matClassName, writer -> {
            writer.write(String.format("""
                            package %1$s;
                                                
                            public class %2$s extends %3$s {
                                public %2$s() {
                                    super(%4$d);
                                }
                                
                                public %2$s(%5$s) {
                                    super(new float[]{%6$s});
                                }
                            }
                            """, generatedPackage, matClassName, "FVector", lanes,
                    args.stream().map(arg -> "float " + arg).collect(Collectors.joining(", ")),
                    String.join(", ", args)));
        });
    }
}
