package ch.szclsb.orbis.buffer;

import ch.szclsb.orbis.driver.foreign.OpenGL;

public enum ValueType {
    FLOAT(OpenGL.GL_FLOAT, 4),
    INT(OpenGL.GL_INT, 4);

    private final int id;
    private final int byteSize;  // size in bytes (not bits)

    ValueType(int id, int byteSize) {
        this.id = id;
        this.byteSize = byteSize;
    }

    public int id() {
        return this.id;
    }

    public int byteSize() {
        return this.byteSize;
    }


}
