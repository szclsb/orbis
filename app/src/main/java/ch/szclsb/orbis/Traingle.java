package ch.szclsb.orbis;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

public class Traingle {
    public static void main(String[] args) {
        try(var session = MemorySession.openShared()) {
            var wrapper = new NativeWrapper();
            run(session, wrapper);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void run(MemorySession session, IGLFW wrapper) throws Throwable {
        if (wrapper.init() == 0) {
            wrapper.terminate();
            return;
        }
        var title = session.allocateUtf8String("Hello World");
        var window = wrapper.createWindow(640, 480, title);
        if (window == MemoryAddress.NULL) {
            wrapper.terminate();
            return;
        }
        wrapper.makeContextCurrent(window);
        while (wrapper.shouldWindowClose(window) == 0) {
            wrapper.clear(IGLFW.GL_COLOR_BUFFER_BIT);
            wrapper.swapBuffers(window);
            wrapper.pollEvents();
        }
        wrapper.terminate();
    }
}
