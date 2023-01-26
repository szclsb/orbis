package ch.szclsb.orbis.shading;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignCharArray;
import ch.szclsb.orbis.foreign.ForeignInt;

import java.lang.foreign.MemoryAddress;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;


public class Program {
    private final int id;

    public Program() {
        this.id = createProgram();
    }

    public int id() {
        return this.id;
    }

    public void attachShader(Shader shader) {
        OpenGL.attachShader(id, shader.id());
    }

    public void link(ForeignInt success, ForeignCharArray infoLog) throws LinkException {
        linkProgram(id);
        getProgramiv(id, GL_LINK_STATUS, success.address());
        if (success.get() == GL_FALSE) {
            getProgramInfoLog(id, infoLog.count(), MemoryAddress.NULL, infoLog.address());
            throw new LinkException(id, infoLog.getString());
        }
    }

    public void use() {
        useProgram(id);
    }
}
