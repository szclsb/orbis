package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.OpenGL;

public enum ShaderType {
    VERTEX(OpenGL.GL_VERTEX_SHADER),
    FRAGMENT(OpenGL.GL_FRAGMENT_SHADER);

    private final int id;

    ShaderType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
