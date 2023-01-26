package ch.szclsb.orbis.buffer;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignIntArray;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;

public class VertexArrayObject {
    private final int id;

    private VertexArrayObject(int id) {
        this.id = id;
    }

    public void bind() {
        bindVertexArray(id);
    }

    public void enableAttribute(int index) {
        enableVertexAttribArray(index);
    }

    public void disableAttribute(int index) {
        disableVertexAttribArray(index);
    }

    public static Stream<VertexArrayObject> create(ForeignIntArray array) {
        createVertexArrays(array.count(), array.address());
        return Arrays.stream(array.toArray()).mapToObj(VertexArrayObject::new);
    }
}
