package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.driver.foreign.Introspector;
import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignString;
import ch.szclsb.orbis.window.Window;
import ch.szclsb.orbis.window.WindowException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.foreign.MemorySession;
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
                try {
                    app.run();
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

    protected Window openWindow(int width, int height, String title) throws WindowException {
        if (GLFW.init() == 0) {
            throw new RuntimeException("Unable to initialize GLFW");
        }
        Window window;
        try(var windowSession = MemorySession.openConfined()) {
            var titleSegment = new ForeignString(windowSession, title);
            window = new Window(width, height, titleSegment);
        }
        window.makeCurrent();
        if (OpenGL.load() == 0) {
            throw new RuntimeException("Unable to initialize OpenGL");
        }
        return window;
    }

    protected abstract void run() throws Exception;
}
