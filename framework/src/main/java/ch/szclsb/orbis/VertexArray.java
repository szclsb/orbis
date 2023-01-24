package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignIntArray;

import java.util.Arrays;
import java.util.stream.Stream;

public class VertexArray {
    private final OpenGL gl;
    private final int id;

    private VertexArray(OpenGL gl, int id) {
        this.gl = gl;
        this.id = id;
    }

    public void bind() {
        gl.bindVertexArray(id);
    }

    public static Stream<VertexArray> create(OpenGL gl, ForeignIntArray array) {
        gl.createVertexArrays(array.count(), array.address());
        return Arrays.stream(array.toArray()).mapToObj(id -> new VertexArray(gl, id));
    }
}
