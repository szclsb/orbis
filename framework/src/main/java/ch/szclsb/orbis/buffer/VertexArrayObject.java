package ch.szclsb.orbis.buffer;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignIntArray;

import java.util.Arrays;
import java.util.stream.Stream;

public class VertexArrayObject {
    private final OpenGL gl;
    private final VertexLayout layout;
    private final int id;

    private VertexArrayObject(OpenGL gl, VertexLayout layout, int id) {
        this.gl = gl;
        this.layout = layout;
        this.id = id;
    }

    public void bind() {
        gl.bindVertexArray(id);
    }

    public void enableAttribute(int index) {
        if (index < 0 || index >= layout.count()) {
            throw new IllegalArgumentException();
        }
        gl.enableVertexAttribArray(index);
    }

    public void enableAllAttribute() {
        for (var i = 0; i < layout.count(); i++) {
            gl.enableVertexAttribArray(i);
        }
    }

    public void disableAttribute(int index) {
        if (index < 0 || index >= layout.count()) {
            throw new IllegalArgumentException();
        }
        gl.disableVertexAttribArray(index);
    }

    public void disableAllAttribute() {
        for (var i = 0; i < layout.count(); i++) {
            gl.disableVertexAttribArray(i);
        }
    }

    public static Stream<VertexArrayObject> create(OpenGL gl, VertexLayout layout, ForeignIntArray array) {
        gl.createVertexArrays(array.count(), array.address());
        return Arrays.stream(array.toArray()).mapToObj(id -> new VertexArrayObject(gl, layout, id));
    }
}
