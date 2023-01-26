package ch.szclsb.orbis.buffer;

import ch.szclsb.orbis.foreign.ForeignFloatArray;
import ch.szclsb.orbis.foreign.ForeignIntArray;

import java.lang.foreign.MemoryAddress;
import java.util.Arrays;
import java.util.stream.Stream;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;

public class ElementBufferObject {
//    private final VertexLayout layout;
    private final int id;

    private ElementBufferObject(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public void bind() {
        bindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public void setData(ForeignIntArray indices, DrawingMode mode) {
        bufferData(GL_ELEMENT_ARRAY_BUFFER, indices.byteSize(), indices.address(), mode.id());
    }

    public static Stream<ElementBufferObject> create(ForeignIntArray array) {
        createBuffers(array.count(), array.address());
        return Arrays.stream(array.toArray()).mapToObj(ElementBufferObject::new);
    }
}
