package ch.szclsb.orbis.app;

import ch.szclsb.orbis.driver.IGLFW;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

public class Triangle {
    public static void main(String[] args) {
        try(var session = MemorySession.openShared()) {
            var glfw = IGLFW.load();
            run(session, glfw);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void run(MemorySession session, IGLFW glfw) throws Throwable {
        if (glfw.init() == 0) {
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
        while (glfw.shouldWindowClose(window) == 0) {
            glfw.clear(IGLFW.GL_COLOR_BUFFER_BIT);
            glfw.swapBuffers(window);
            glfw.pollEvents();
        }
        glfw.terminate();
    }
}
