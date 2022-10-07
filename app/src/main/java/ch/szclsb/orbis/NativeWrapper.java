package ch.szclsb.orbis;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class NativeWrapper implements IGLFW {
    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();

    private static MemorySegment loadSymbol(String name) {
        return LOADER.lookup(name).orElseThrow(() -> new UnsatisfiedLinkError("unable to find symbol " + name));
    }

    private final MethodHandle glfwInit;
    private final MethodHandle glfwTerminate;
    private final MethodHandle glfwCreateWindow;
    private final MethodHandle glfwMakeContextCurrent;
    private final MethodHandle glfwWindowShouldClose;
    private final MethodHandle glClear;
    private final MethodHandle glfwSwapBuffers;
    private final MethodHandle glfwPollEvents;

    public NativeWrapper() {
        var dir = System.getProperty("user.dir");
        System.loadLibrary("opengl32");
        System.load(dir + "/glfw/glfw3.dll");

        this.glfwInit = LINKER.downcallHandle(loadSymbol("glfwInit"), FunctionDescriptor.of(ValueLayout.JAVA_INT));
        this.glfwTerminate = LINKER.downcallHandle(loadSymbol("glfwTerminate"), FunctionDescriptor.ofVoid());
        this.glfwCreateWindow = LINKER.downcallHandle(loadSymbol("glfwCreateWindow"), FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
        this.glfwMakeContextCurrent = LINKER.downcallHandle(loadSymbol("glfwMakeContextCurrent"), FunctionDescriptor.ofVoid(ValueLayout.ADDRESS));
        this.glfwWindowShouldClose = LINKER.downcallHandle(loadSymbol("glfwWindowShouldClose"), FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
        this.glClear = LINKER.downcallHandle(loadSymbol("glClear"), FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT));
        this.glfwSwapBuffers = LINKER.downcallHandle(loadSymbol("glfwSwapBuffers"), FunctionDescriptor.ofVoid(ValueLayout.ADDRESS));;
        this.glfwPollEvents = LINKER.downcallHandle(loadSymbol("glfwPollEvents"), FunctionDescriptor.ofVoid());
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
    public int clear(int bitmask) throws Throwable {
        return (int) glClear.invoke(bitmask);
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
