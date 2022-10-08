package ch.szclsb.orbis.app;

import ch.szclsb.orbis.driver.GLFW;
import ch.szclsb.orbis.driver.OpenGL;
import ch.szclsb.orbis.driver.foreign.ForeignGLFW;
import ch.szclsb.orbis.driver.foreign.ForeignOpenGL;
import ch.szclsb.orbis.driver.foreign.Introspector;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

import static ch.szclsb.orbis.driver.OpenGL.*;

public class Triangle {
    public static void main(String[] args) {
        Introspector.loadLibraries();
        try(var session = MemorySession.openShared()) {
            var glfw = new ForeignGLFW();
            var gl = new ForeignOpenGL();
            run(session, glfw, gl);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void run(MemorySession session, GLFW glfw, OpenGL gl) throws Throwable {
        if (glfw.init() == GL_FALSE) {
            glfw.terminate();
            return;
        }
        var title = session.allocateUtf8String("Hello World");
        var window = glfw.createWindow(640, 480, title);
        if (window == MemoryAddress.NULL) {
            glfw.terminate();
            return;
        }
        glfw.makeContextCurrent(window);
        while (glfw.shouldWindowClose(window) == GL_FALSE) {
            gl.clear(GL_COLOR_BUFFER_BIT);
            glfw.swapBuffers(window);
            glfw.pollEvents();
        }
        glfw.terminate();
    }
}
