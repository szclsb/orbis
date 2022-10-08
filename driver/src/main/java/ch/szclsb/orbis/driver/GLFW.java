package ch.szclsb.orbis.driver;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;

public interface GLFW {
    int init() throws Throwable;

    void terminate() throws Throwable;

    MemoryAddress createWindow(int width, int height, MemorySegment title) throws Throwable;

    void makeContextCurrent(MemoryAddress window) throws Throwable;

    int shouldWindowClose(MemoryAddress window) throws Throwable;

    void swapBuffers(MemoryAddress window) throws Throwable;

    void pollEvents() throws Throwable;
}
