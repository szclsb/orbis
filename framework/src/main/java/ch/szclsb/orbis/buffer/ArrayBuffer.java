package ch.szclsb.orbis.buffer;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignFloatArray;
import ch.szclsb.orbis.foreign.ForeignIntArray;

import java.lang.foreign.MemoryAddress;
import java.util.Arrays;
import java.util.stream.Stream;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;
import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_FALSE;

public class ArrayBuffer {
    private final OpenGL gl;
    private final int id;

    private ArrayBuffer(OpenGL gl, int id) {
        this.gl = gl;
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public void bind() {
        gl.bindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void init(ValueType type, int vertexSize, boolean normalized) {
        gl.vertexAttribPointer(0, vertexSize, type.id(), normalized ? GL_TRUE : GL_FALSE,
                vertexSize * type.byteSize(), MemoryAddress.NULL);
        gl.enableVertexAttribArray(0);
    }

    public void setData(ForeignFloatArray vertices, DrawingMode mode) {
        gl.bufferData(GL_ARRAY_BUFFER, vertices.byteSize(), vertices.address(), mode.id());
    }

    public static Stream<ArrayBuffer> create(OpenGL gl, ForeignIntArray array) {
        gl.createBuffers(array.count(), array.address());
        return Arrays.stream(array.toArray()).mapToObj(id -> new ArrayBuffer(gl, id));
    }
}
