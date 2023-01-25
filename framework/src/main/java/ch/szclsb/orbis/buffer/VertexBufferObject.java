package ch.szclsb.orbis.buffer;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignFloatArray;
import ch.szclsb.orbis.foreign.ForeignIntArray;

import java.lang.foreign.MemoryAddress;
import java.util.Arrays;
import java.util.stream.Stream;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;
import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_FALSE;

public class VertexBufferObject {
    private final OpenGL gl;
    private final VertexLayout layout;
    private final int id;

    private VertexBufferObject(OpenGL gl, VertexLayout layout, int id) {
        this.gl = gl;
        this.layout = layout;
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public void bind() {
        gl.bindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void init(boolean normalized, Object format) {
        // todo vertex format: const, interleaved
        var i = 0;
        var it = layout.attributeStream().iterator();
        while (it.hasNext()) {
            var va = it.next();
            gl.vertexAttribPointer(i++, va.count(), va.type().id(), normalized ? GL_TRUE : GL_FALSE,
                    va.count() * va.type().byteSize(), MemoryAddress.NULL);
        }
    }

    public void setData(ForeignFloatArray vertices, DrawingMode mode) {
        gl.bufferData(GL_ARRAY_BUFFER, vertices.byteSize(), vertices.address(), mode.id());
    }

    public static Stream<VertexBufferObject> create(OpenGL gl, VertexLayout layout, ForeignIntArray array) {
        gl.createBuffers(array.count(), array.address());
        return Arrays.stream(array.toArray()).mapToObj(id -> new VertexBufferObject(gl, layout, id));
    }
}
