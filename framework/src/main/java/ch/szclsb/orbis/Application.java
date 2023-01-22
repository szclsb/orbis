package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.driver.foreign.Introspector;
import ch.szclsb.orbis.driver.foreign.OpenGL;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.foreign.MemorySession;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Application {
    @SuppressWarnings("unchecked")
    private static Class<? extends Application> cast(Class<?> clazz) {
        if (Application.class.isAssignableFrom(clazz)) {
            return (Class<? extends Application>) clazz;
        }
        return null;
    }

    public static void launch(String[] args) {
        try {
            var cause = Thread.currentThread().getStackTrace();
            var i = 0;
            Class<? extends Application> appClass = null;
            while (i < cause.length && appClass == null) {
                var se = cause[i];
                if (Application.class.getName().equals(se.getClassName()) && "launch".equals(se.getMethodName())) {
                    appClass = cast(Class.forName(cause[i + 1].getClassName(), false,
                            Thread.currentThread().getContextClassLoader()));
                }
                i++;
            }

            if (appClass == null) {
                throw new RuntimeException("Error: unable to determine Application class");
            }

            try {
                var app = appClass.getConstructor().newInstance();

                Introspector.loadLibraries();
                try (var session = MemorySession.openShared()) {
                    var glfw = new GLFW();
                    var gl = new OpenGL();

                    app.run(session, glfw, gl);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchMethodException me) {
                throw new RuntimeException("Error: no zero args constructor in Application class");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String readResource(String resource) throws IOException {
        try(var reader = new BufferedReader(new InputStreamReader(Optional.ofNullable(getClass().getResourceAsStream(resource))
                .orElseThrow(() -> new FileNotFoundException(resource))))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    protected abstract void run(MemorySession session, GLFW glfw, OpenGL gl) throws Exception;
}
