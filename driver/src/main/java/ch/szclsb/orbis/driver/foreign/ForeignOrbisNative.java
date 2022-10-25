package ch.szclsb.orbis.driver.foreign;

import ch.szclsb.orbis.driver.*;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

import static ch.szclsb.orbis.driver.OpenGL.*;
import static ch.szclsb.orbis.driver.foreign.Introspector.loadMethod;
import static java.lang.foreign.ValueLayout.*;

public class ForeignOrbisNative implements IOrbisNative {
    private final MemorySession session;

    private final MethodHandle nativeCreateWindow;
    private final MethodHandle nativeCreateShader;
    private final MethodHandle nativeCompileShader;
    private final MethodHandle nativeDeleteShader;
    private final MethodHandle nativeCreateProgram;
    private final MethodHandle nativeAttachShader;
    private final MethodHandle nativeLinkProgram;
    private final MethodHandle nativeUseProgram;
    private final MethodHandle nativeCreateVertexArray;
    private final MethodHandle nativeBindVertexArray;
    private final MethodHandle nativeCreateBuffer;
    private final MethodHandle nativeBindBuffer;
    private final MethodHandle nativeBufferData;
    private final MethodHandle nativeVertexAttribPointer;
    private final MethodHandle nativeEnableVertexAttribArray;
    private final MethodHandle nativeClear;
    private final MethodHandle nativeDrawArrays;
    private final MethodHandle nativeShouldWindowClose;
    private final MethodHandle nativeRenderWindow;
    private final MethodHandle nativeShutDown;

    public ForeignOrbisNative(MemorySession session) {
        this.session = session;
        this.nativeCreateWindow = loadMethod("createWindow", ADDRESS, JAVA_INT, JAVA_INT, ADDRESS);
        this.nativeCreateShader = loadMethod("createShader", JAVA_INT, JAVA_INT);
        this.nativeCompileShader = loadMethod("compileShader", JAVA_INT, JAVA_INT, ADDRESS, ADDRESS, JAVA_INT);
        this.nativeDeleteShader = loadMethod("deleteShader", null, JAVA_INT);
        this.nativeCreateProgram = loadMethod("createProgram", JAVA_INT);
        this.nativeAttachShader = loadMethod("attachShader", null, JAVA_INT, JAVA_INT);
        this.nativeLinkProgram = loadMethod("linkProgram", JAVA_INT, JAVA_INT, ADDRESS, JAVA_INT);
        this.nativeUseProgram = loadMethod("useProgram", null, JAVA_INT);
        this.nativeCreateVertexArray = loadMethod("createVertexArray", JAVA_INT);
        this.nativeBindVertexArray = loadMethod("bindVertexArray", null, JAVA_INT);
        this.nativeCreateBuffer = loadMethod("createBuffer", JAVA_INT);
        this.nativeBindBuffer = loadMethod("bindBuffer", null, JAVA_INT, JAVA_INT);
        this.nativeBufferData = loadMethod("bufferData", null, JAVA_INT, ADDRESS, JAVA_INT, JAVA_INT);
        this.nativeVertexAttribPointer = loadMethod("vertexAttribPointer", null, JAVA_INT, JAVA_INT, JAVA_INT, JAVA_INT, JAVA_INT);
        this.nativeEnableVertexAttribArray = loadMethod("enableVertexAttribArray", null, JAVA_INT);
        this.nativeClear = loadMethod("clear", null, JAVA_INT);
        this.nativeDrawArrays = loadMethod("drawArrays", null, JAVA_INT, JAVA_INT, JAVA_INT);
        this.nativeShouldWindowClose = loadMethod("shouldWindowClose", JAVA_INT, ADDRESS);
        this.nativeRenderWindow = loadMethod("renderWindow", null, ADDRESS);
        this.nativeShutDown = loadMethod("shutDown", null);
    }

    @Override
    public long createWindow(int width, int height, String title) throws Throwable {
        var nativeTitle = session.allocateUtf8String(title);
        var addr = (MemoryAddress) nativeCreateWindow.invoke(width, height, nativeTitle.address());
        return addr.toRawLongValue();
    }

    @Override
    public int createShader(int type) throws Throwable {
        return (int) nativeCreateShader.invoke(type);
    }

    @Override
    public void compileShader(int shader, String source, Consumer<String> errorCallback) throws Throwable {
        var nativeSource = session.allocateUtf8String(source);
        var errorLog = session.allocateArray(JAVA_CHAR, 512);
        var success = (int) nativeCompileShader.invoke(shader, nativeSource.address(), errorLog.address(), 512);
        if (success == GL_FALSE) {
            errorCallback.accept(errorLog.getUtf8String(0));
        }
    }

    @Override
    public void deleteShader(int shader) throws Throwable {
        nativeDeleteShader.invoke(shader);
    }

    @Override
    public int createProgram() throws Throwable {
        return (int) nativeCreateProgram.invoke();
    }

    @Override
    public void attachShader(int program, int shader) throws Throwable {
        nativeAttachShader.invoke(program, shader);
    }

    @Override
    public void linkProgram(int program, Consumer<String> errorCallback) throws Throwable {
        var errorLog = session.allocateArray(JAVA_CHAR, 512);
        var success = (int) nativeLinkProgram.invoke(program, errorLog, 512);
        if (success == GL_FALSE) {
            errorCallback.accept(errorLog.getUtf8String(0));
        }
    }

    @Override
    public void useProgram(int program) throws Throwable {
        nativeUseProgram.invoke(program);
    }

    @Override
    public int createVertexArray() throws Throwable {
        return (int) nativeCreateVertexArray.invoke();
    }

    @Override
    public void bindVertexArray(int vao) throws Throwable {
        nativeBindVertexArray.invoke(vao);
    }

    @Override
    public int createBuffer() throws Throwable {
        return (int) nativeCreateBuffer.invoke();
    }

    @Override
    public void bindBuffer(int target, int vbo) throws Throwable {
        nativeBindBuffer.invoke(target, vbo);
    }

    @Override
    public void bufferData(int target, float[] vertices, int usage) throws Throwable {
        var segment = session.allocateArray(JAVA_FLOAT, vertices);
        nativeBufferData.invoke(target, segment.address(), vertices.length, usage);
    }

    @Override
    public void vertexAttribPointer(int index, int size, int type, int normalized, int stride) throws Throwable {
        nativeVertexAttribPointer.invoke(index, size, type, normalized, stride);
    }

    @Override
    public void enableVertexAttribArray(int index) throws Throwable {
        nativeEnableVertexAttribArray.invoke(index);
    }

    @Override
    public void clear(int bitmask) throws Throwable {
        nativeClear.invoke(bitmask);
    }

    @Override
    public void drawArrays(int mode, int first, int count) throws Throwable {
        nativeDrawArrays.invoke(mode, first, count);
    }

    @Override
    public int shouldWindowClose(long window) throws Throwable {
        var windowAddress = MemoryAddress.ofLong(window);
        return (int) nativeShouldWindowClose.invoke(windowAddress);
    }

    @Override
    public void renderWindow(long window) throws Throwable {
        var windowAddress = MemoryAddress.ofLong(window);
        nativeRenderWindow.invoke(windowAddress);
    }

    //    @Override
//    public void renderWindow(long window, IRenderCallback renderCallback) throws Throwable {
//        var windowAddress = MemoryAddress.ofLong(window);
//        while((int) nativeShouldWindowClose.invoke(windowAddress) == GL_FALSE) {
//            nativeClear.invoke(GL_COLOR_BUFFER_BIT);
//            renderCallback.render(0); //fixme
//            nativeRenderWindow.invoke(windowAddress);
//        }
//    }

    @Override
    public void shutDown() throws Throwable {
        nativeShutDown.invoke();
    }
}
