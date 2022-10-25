package ch.szclsb.orbis.driver;

import java.util.function.Consumer;

public interface IOrbisNative {
    long createWindow(int width, int height, String title) throws Throwable;

    int createShader(int type) throws Throwable;
    void compileShader(int shader, String source, Consumer<String> errorCallback) throws Throwable;
    void deleteShader(int shader) throws Throwable;
    int createProgram() throws Throwable;
    void attachShader(int program, int shader) throws Throwable;
    void linkProgram(int program, Consumer<String> errorCallback) throws Throwable;
    void useProgram(int program) throws Throwable;

    int createVertexArray() throws Throwable;
    void bindVertexArray(int vao) throws Throwable;
    int createBuffer() throws Throwable;

    void bindBuffer(int target, int vbo) throws Throwable;

    void bufferData(int target, float[] vertices, int usage) throws Throwable;

    void vertexAttribPointer(int index, int size, int type, int normalized, int stride) throws Throwable;

    void enableVertexAttribArray(int index) throws Throwable;

    void drawArrays(int mode, int first, int count) throws Throwable;

    void clear(int bitmask) throws Throwable;

    int shouldWindowClose(long window) throws Throwable;

    void renderWindow(long window) throws Throwable;

    void shutDown() throws Throwable;
}
