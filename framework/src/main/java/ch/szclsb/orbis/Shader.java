package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignCharArray;
import ch.szclsb.orbis.foreign.ForeignInt;
import ch.szclsb.orbis.foreign.ForeignString;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.ValueLayout;

import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_COMPILE_STATUS;
import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_FALSE;

public class Shader implements AutoCloseable {

    private final OpenGL gl;
    private final ShaderType type;
    private final int id;

    public Shader(OpenGL gl, ShaderType type) {
        this.gl = gl;
        this.type = type;
        this.id = gl.createShader(type.getId());
    }

    public ShaderType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void compile(ForeignString source, ForeignInt success, ForeignCharArray infoLog) throws CompileException {
        gl.shaderSource(id, 1, source.getAddress(), MemoryAddress.NULL);
        gl.compileShader(id);
        gl.getShaderiv(id, GL_COMPILE_STATUS, success.getAddress());
        if (success.get() == GL_FALSE) {
            gl.getShaderInfoLog(id, infoLog.getSize(), MemoryAddress.NULL, infoLog.getAddress());
            throw new CompileException(id, type, infoLog.getString());
        }
    }

    @Override
    public void close() throws Exception {
        gl.deleteShader(id);
    }
}
