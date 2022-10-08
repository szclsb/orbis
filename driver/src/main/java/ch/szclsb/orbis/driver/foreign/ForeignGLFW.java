package ch.szclsb.orbis.driver.foreign;

import ch.szclsb.orbis.driver.GLFW;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static ch.szclsb.orbis.driver.foreign.Introspector.loadMethod;
import static java.lang.foreign.ValueLayout.*;

public class ForeignGLFW implements GLFW {
    private final MethodHandle glfwInit;
    private final MethodHandle glfwTerminate;
    private final MethodHandle glfwCreateWindow;
    private final MethodHandle glfwMakeContextCurrent;
    private final MethodHandle glfwWindowShouldClose;
    private final MethodHandle glfwSwapBuffers;
    private final MethodHandle glfwPollEvents;

    public ForeignGLFW() {
        this.glfwInit               = loadMethod("glfwInit", JAVA_INT);
        this.glfwTerminate          = loadMethod("glfwTerminate", null);
        this.glfwCreateWindow       = loadMethod("glfwCreateWindow", ADDRESS, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS, ADDRESS);
        this.glfwMakeContextCurrent = loadMethod("glfwMakeContextCurrent", null, ADDRESS);
        this.glfwWindowShouldClose  = loadMethod("glfwWindowShouldClose", JAVA_INT, ADDRESS);
        this.glfwSwapBuffers        = loadMethod("glfwSwapBuffers", null, ADDRESS);
        this.glfwPollEvents         = loadMethod("glfwPollEvents", null);
    }

    @Override
    public int init() throws Throwable {
        return (int) glfwInit.invoke();
    }

    @Override
    public void terminate() throws Throwable {
        glfwTerminate.invoke();
    }

    @Override
    public MemoryAddress createWindow(int width, int height, MemorySegment title) throws Throwable {
        return (MemoryAddress) glfwCreateWindow.invoke(width, height, title, MemoryAddress.NULL, MemoryAddress.NULL);
    }

    @Override
    public void makeContextCurrent(MemoryAddress window) throws Throwable {
        glfwMakeContextCurrent.invoke(window);
    }

    @Override
    public int shouldWindowClose(MemoryAddress window) throws Throwable {
        return (int) glfwWindowShouldClose.invoke(window);
    }

    @Override
    public void swapBuffers(MemoryAddress window) throws Throwable {
        glfwSwapBuffers.invoke(window);
    }

    @Override
    public void pollEvents() throws Throwable {
        glfwPollEvents.invoke();
    }
}
