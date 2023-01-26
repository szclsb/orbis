package ch.szclsb.orbis.buffer;

import ch.szclsb.orbis.foreign.ForeignFloatArray;
import ch.szclsb.orbis.foreign.ForeignIntArray;

import java.lang.foreign.MemoryAddress;
import java.util.Arrays;
import java.util.stream.Stream;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;

public class VertexBufferObject {
//    private final VertexLayout layout;
    private final int id;

    private VertexBufferObject(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public void bind() {
        bindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void init(int index, int count, ValueType valueType, boolean normalized) {
        // todo support other vertex formats
        vertexAttribPointer(index, count, valueType.id(), normalized ? GL_TRUE : GL_FALSE,
                    count * valueType.byteSize(), MemoryAddress.NULL);
    }

//    public void init(boolean normalized, Object format) {
        // todo vertex format: const, interleaved
//        var i = 0;
//        var it = layout.attributeStream().iterator();
//        while (it.hasNext()) {
//            var va = it.next();
//            gl.vertexAttribPointer(i++, va.count(), va.type().id(), normalized ? GL_TRUE : GL_FALSE,
//                    va.count() * va.type().byteSize(), MemoryAddress.NULL);
//        }
//    }

    public void setData(ForeignFloatArray vertices, DrawingMode mode) {
        bufferData(GL_ARRAY_BUFFER, vertices.byteSize(), vertices.address(), mode.id());
    }

    public static Stream<VertexBufferObject> create(ForeignIntArray array) {
        createBuffers(array.count(), array.address());
        return Arrays.stream(array.toArray()).mapToObj(VertexBufferObject::new);
    }
}
