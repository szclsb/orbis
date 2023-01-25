package ch.szclsb.orbis.shading;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignCharArray;
import ch.szclsb.orbis.foreign.ForeignInt;

import java.lang.foreign.MemoryAddress;

import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_FALSE;
import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_LINK_STATUS;

public class Program {
    private final OpenGL gl;
    private final int id;

    public Program(OpenGL gl) {
        this.gl = gl;
        this.id = gl.createProgram();
    }

    public int id() {
        return this.id;
    }

    public void attachShader(Shader shader) {
        gl.attachShader(id, shader.id());
    }

    public void link(ForeignInt success, ForeignCharArray infoLog) throws LinkException {
        gl.linkProgram(id);
        gl.getProgramiv(id, GL_LINK_STATUS, success.address());
        if (success.get() == GL_FALSE) {
            gl.getProgramInfoLog(id, infoLog.count(), MemoryAddress.NULL, infoLog.address());
            throw new LinkException(id, infoLog.getString());
        }
    }

    public void use() {
        gl.useProgram(id);
    }
}
