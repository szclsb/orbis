package ch.szclsb.orbis.driver.foreign;

import java.lang.foreign.MemoryAddress;
import java.lang.invoke.MethodHandle;

import static ch.szclsb.orbis.driver.foreign.Introspector.loadMethod;
import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public class GLFW {
    private final MethodHandle glfwInit;
    private final MethodHandle glfwCreateWindow;
    private final MethodHandle glfwMakeContextCurrent;
    private final MethodHandle glfwWindowShouldClose;
    private final MethodHandle glfwSwapBuffers;
    private final MethodHandle glfwPollEvents;
    private final MethodHandle glfwShutDown;

    public GLFW() {
        this.glfwInit = loadMethod("init", JAVA_INT);
        this.glfwCreateWindow = loadMethod("createWindow", ADDRESS, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS, ADDRESS);
        this.glfwMakeContextCurrent = loadMethod("makeContextCurrent", null, ADDRESS);
        this.glfwWindowShouldClose = loadMethod("windowShouldClose", JAVA_INT, ADDRESS);
        this.glfwSwapBuffers = loadMethod("swapBuffers", null, ADDRESS);
        this.glfwPollEvents = loadMethod("pollEvents", null);
        this.glfwShutDown = loadMethod("shutDown", null);
    }

    public int init() {
        try {
            return (int) glfwInit.invoke();
        } catch (Throwable th) {
            throw new RuntimeException("Error invoking native method glfw.init()", th);
        }
    }

    public MemoryAddress createWindow(int width, int height, MemoryAddress title, MemoryAddress monitor, MemoryAddress share) {
        try {
            return (MemoryAddress) glfwCreateWindow.invoke(width, height, title, monitor, share);
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Error invoking native method glfw.create_window(%d, %d, &%d, &%d, &%d)",
                    width, height, title.toRawLongValue(), monitor.toRawLongValue(), share.toRawLongValue()), th);
        }
    }

    public void makeContextCurrent(MemoryAddress window) {
        try {
            glfwMakeContextCurrent.invoke(window);
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Error invoking native method glfw.make_context_current(&%d)",
                    window.toRawLongValue()), th);
        }
    }

    public int windowShouldClose(MemoryAddress window) {
        try {
            return (int) glfwWindowShouldClose.invoke(window);
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Error invoking native method glfw.window_should_close(&%d)",
                    window.toRawLongValue()), th);
        }
    }

    public void swapBuffers(MemoryAddress window) {
        try {
            glfwSwapBuffers.invoke(window);
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Error invoking native method glfw.swap_buffers(&%d)",
                    window.toRawLongValue()), th);
        }
    }

    public void pollEvents() {
        try {
            glfwPollEvents.invoke();
        } catch (Throwable th) {
            throw new RuntimeException("Error invoking native method glfw.poll_events()", th);
        }
    }

    public void shutDown() {
        try {
            glfwShutDown.invoke();
        } catch (Throwable th) {
            throw new RuntimeException("Error invoking native method glfw.shut_down()", th);
        }
    }
}
