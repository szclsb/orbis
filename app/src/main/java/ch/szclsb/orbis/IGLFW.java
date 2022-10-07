package ch.szclsb.orbis;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;

public interface IGLFW {
    int GL_COLOR_BUFFER_BIT = 0x00004000;

    int init() throws Throwable;
    void terminate() throws Throwable;
    MemoryAddress createWindow(int width, int height, MemorySegment title) throws Throwable;

    void makeContextCurrent(MemoryAddress window) throws Throwable;

    int shouldWindowClose(MemoryAddress window) throws Throwable;

    int clear(int bitmask) throws Throwable;

    void swapBuffers(MemoryAddress window) throws Throwable;

    void pollEvents() throws Throwable;
}
