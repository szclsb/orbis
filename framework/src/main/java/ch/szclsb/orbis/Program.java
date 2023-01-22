package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.OpenGL;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_FALSE;
import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_LINK_STATUS;

public class Program {
    private static int bufSize = 512;

    private final OpenGL gl;
    private final int id;

    public Program(OpenGL gl) {
        this.gl = gl;
        this.id = gl.createProgram();
    }

    public int getId() {
        return id;
    }

    public void attachShader(Shader shader) {
        gl.attachShader(id, shader.getId());
    }

    public void link(MemorySession session) throws LinkException {
        var success = session.allocate(ValueLayout.JAVA_INT);
        var infoLog = session.allocateArray(ValueLayout.JAVA_CHAR, bufSize);
        gl.linkProgram(id);
        gl.getProgramiv(id, GL_LINK_STATUS, success.address());
        if (success.get(ValueLayout.JAVA_INT, 0) == GL_FALSE) {
            gl.getProgramInfoLog(id, bufSize, MemoryAddress.NULL, infoLog.address());
            throw new LinkException(id, infoLog.getUtf8String(0));
        }
    }

    public void use() {
        gl.useProgram(id);
    }
}
