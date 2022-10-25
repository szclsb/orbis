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

    public int init() throws Throwable {
        return (int) glfwInit.invoke();
    }

    public MemoryAddress createWindow(int width, int height, MemoryAddress title, MemoryAddress monitor, MemoryAddress share) throws Throwable {
        return (MemoryAddress) glfwCreateWindow.invoke(width, height, title, monitor, share);
    }

    public void makeContextCurrent(MemoryAddress window) throws Throwable {
        glfwMakeContextCurrent.invoke(window);
    }

    public int windowShouldClose(MemoryAddress window) throws Throwable {
        return (int) glfwWindowShouldClose.invoke(window);
    }

    public void swapBuffers(MemoryAddress window) throws Throwable {
        glfwSwapBuffers.invoke(window);
    }

    public void pollEvents() throws Throwable {
        glfwPollEvents.invoke();
    }

    public void shutDown() throws Throwable {
        glfwShutDown.invoke();
    }
}
