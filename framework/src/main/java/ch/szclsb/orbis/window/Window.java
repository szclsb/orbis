package ch.szclsb.orbis.window;

import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.foreign.ForeignString;

import java.lang.foreign.MemoryAddress;

import static ch.szclsb.orbis.driver.foreign.GLFW.*;

public class Window {
    private final MemoryAddress address;

    public Window(int width, int height, ForeignString title) throws WindowException {
        this.address = createWindow(width, height, title.address(), MemoryAddress.NULL, MemoryAddress.NULL);
        if (address == MemoryAddress.NULL) {
            throw new WindowException();
        }
    }

    public void makeContextCurrent() {
        GLFW.makeContextCurrent(address);
    }

    public boolean shouldClose() {
        return windowShouldClose(address) == 1;
    }

    public void swapBuffer() {
        swapBuffers(address);
    }
}
