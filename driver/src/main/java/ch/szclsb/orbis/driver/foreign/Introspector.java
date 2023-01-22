package ch.szclsb.orbis.driver.foreign;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;

public class Introspector {
    private static final Path LIB_DIR = Path.of(System.getProperty("user.dir"), "driver");
    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();

    /**
     * Call before load symbol or loadMethod
     */
    public static void loadLibraries() {
//        System.loadLibrary("opengl32");
//        loadProvidedLibrary("glew32.dll");
//        loadProvidedLibrary( "glfw3.dll");
        loadProvidedLibrary( "orbis_native_graphic.dll");
    }

    private static void loadProvidedLibrary(String name) {
        var libPath = LIB_DIR.resolve(name);
        System.load(libPath.toAbsolutePath().toString());
    }

    public static MemorySegment loadSymbol(String name) {
        return LOADER.lookup(name).orElseThrow(() -> new UnsatisfiedLinkError("unable to find symbol " + name));
    }

    public static MethodHandle loadMethod(String name, MemoryLayout returnLayout, MemoryLayout... argsLayout) {
        var symbol = loadSymbol(name);
        var descriptor = returnLayout != null
                ? FunctionDescriptor.of(returnLayout, argsLayout)
                : FunctionDescriptor.ofVoid(argsLayout);
        return LINKER.downcallHandle(symbol, descriptor);
    }

//    public static MemorySegment upcall(MemorySession session, MethodHandle handle, MemoryLayout returnLayout, MemoryLayout... argsLayout) {
//        var descriptor = returnLayout != null
//                ? FunctionDescriptor.of(returnLayout, argsLayout)
//                : FunctionDescriptor.ofVoid(argsLayout);
//        return LINKER.upcallStub(handle, descriptor, session);
//    }
}
