package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.foreign.ForeignString;

import java.lang.foreign.MemoryAddress;

public class Window {
    private final GLFW glfw;
    private final MemoryAddress address;

    public Window(GLFW glfw, int width, int height, ForeignString title) throws WindowException {
        this.glfw = glfw;
        this.address = glfw.createWindow(width, height, title.getAddress(), MemoryAddress.NULL, MemoryAddress.NULL);
        if (address == MemoryAddress.NULL) {
            throw new WindowException();
        }
    }

    public void makeCurrent() {
        glfw.makeContextCurrent(address);
    }

    public boolean shouldClose() {
        return glfw.windowShouldClose(address) == 1;
    }

    public void swapBuffer() {
        glfw.swapBuffers(address);
    }
}
