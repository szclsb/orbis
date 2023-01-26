package ch.szclsb.orbis.driver.foreign;

import java.lang.foreign.MemoryAddress;
import java.lang.invoke.MethodHandle;

import static ch.szclsb.orbis.driver.foreign.Introspector.loadMethod;
import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class GLFW {
    private GLFW() {}

    private static final MethodHandle glfwInit = loadMethod("init", JAVA_INT);
    private static final MethodHandle glfwCreateWindow = loadMethod("createWindow", ADDRESS, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS, ADDRESS);
    private static final MethodHandle glfwMakeContextCurrent = loadMethod("makeContextCurrent", null, ADDRESS);
    private static final MethodHandle glfwWindowShouldClose = loadMethod("windowShouldClose", JAVA_INT, ADDRESS);
    private static final MethodHandle glfwSwapBuffers = loadMethod("swapBuffers", null, ADDRESS);
    private static final MethodHandle glfwPollEvents = loadMethod("pollEvents", null);
    private static final MethodHandle glfwShutDown = loadMethod("shutDown", null);

    public static int init() {
        try {
            return (int) glfwInit.invoke();
        } catch (Throwable th) {
            throw new RuntimeException("Error invoking native method glfw.init()", th);
        }
    }

    public static MemoryAddress createWindow(int width, int height, MemoryAddress title, MemoryAddress monitor, MemoryAddress share) {
        try {
            return (MemoryAddress) glfwCreateWindow.invoke(width, height, title, monitor, share);
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Error invoking native method glfw.create_window(%d, %d, &%d, &%d, &%d)",
                    width, height, title.toRawLongValue(), monitor.toRawLongValue(), share.toRawLongValue()), th);
        }
    }

    public static void makeContextCurrent(MemoryAddress window) {
        try {
            glfwMakeContextCurrent.invoke(window);
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Error invoking native method glfw.make_context_current(&%d)",
                    window.toRawLongValue()), th);
        }
    }

    public static int windowShouldClose(MemoryAddress window) {
        try {
            return (int) glfwWindowShouldClose.invoke(window);
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Error invoking native method glfw.window_should_close(&%d)",
                    window.toRawLongValue()), th);
        }
    }

    public static void swapBuffers(MemoryAddress window) {
        try {
            glfwSwapBuffers.invoke(window);
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Error invoking native method glfw.swap_buffers(&%d)",
                    window.toRawLongValue()), th);
        }
    }

    public static void pollEvents() {
        try {
            glfwPollEvents.invoke();
        } catch (Throwable th) {
            throw new RuntimeException("Error invoking native method glfw.poll_events()", th);
        }
    }

    public static void shutDown() {
        try {
            glfwShutDown.invoke();
        } catch (Throwable th) {
            throw new RuntimeException("Error invoking native method glfw.shut_down()", th);
        }
    }
}
