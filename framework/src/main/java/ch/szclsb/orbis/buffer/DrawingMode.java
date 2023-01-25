package ch.szclsb.orbis.buffer;

import ch.szclsb.orbis.driver.foreign.OpenGL;

public enum DrawingMode {
    STREAM_DRAW(OpenGL.GL_STREAM_DRAW),
    STREAM_READ(OpenGL.GL_STREAM_READ),
    STREAM_COPY(OpenGL.GL_STREAM_COPY),
    STATIC_DRAW(OpenGL.GL_STATIC_DRAW),
    STATIC_READ(OpenGL.GL_STATIC_READ),
    STATIC_COPY(OpenGL.GL_STATIC_COPY),
    DYNAMIC_DRAW(OpenGL.GL_DYNAMIC_DRAW),
    DYNAMIC_READ(OpenGL.GL_DYNAMIC_READ),
    DYNAMIC_COPY(OpenGL.GL_DYNAMIC_COPY);

    private final int id;

    DrawingMode(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }
}
