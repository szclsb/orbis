package ch.szclsb.orbis.driver.impl;

import ch.szclsb.orbis.driver.IGLFW;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Files;
import java.nio.file.Path;

public class NativeWrapper implements IGLFW {
    private static final Path LIB_DIR = Path.of(System.getProperty("user.dir"));
    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();

    private static void loadProvidedLibrary(String name) throws IOException {
        var libPath = LIB_DIR.resolve(name);
        if (!Files.exists(libPath)) {
            throw new FileNotFoundException(String.format("Library %s was not found int the directory <EXEC_JAR>/libs", name));
        }
        System.load(libPath.toAbsolutePath().toString());
    }

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

    public NativeWrapper() throws Exception {
        System.loadLibrary("opengl32");
        loadProvidedLibrary( "glfw3.dll");
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
    public void clear(int bitmask) throws Throwable {
        glClear.invoke(bitmask);
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
