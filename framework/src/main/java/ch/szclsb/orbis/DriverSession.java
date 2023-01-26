package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.driver.foreign.OpenGL;

public class DriverSession implements AutoCloseable {
    private DriverSession() {}

    public static DriverSession open() {
        // init GLFW first
        if (GLFW.init() == 0) {
            throw new RuntimeException("Unable to initialize GLFW");
        }
        return new DriverSession();
    }

    /**
     * init OpenGL after any window has context, this uses GLAD as OpenGL loader
     */
    public void initOpenGL() {
        if (OpenGL.load() == 0) {
            throw new RuntimeException("Unable to initialize OpenGL");
        }
    }

    @Override
    public void close() throws Exception {
        GLFW.shutDown();
    }
}
