package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignCharArray;
import ch.szclsb.orbis.foreign.ForeignInt;
import ch.szclsb.orbis.foreign.ForeignString;

import java.lang.foreign.MemoryAddress;

import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_COMPILE_STATUS;
import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_FALSE;

public class Shader implements AutoCloseable {
    private final OpenGL gl;
    private final ShaderType type;
    private final int id;

    public Shader(OpenGL gl, ShaderType type) {
        this.gl = gl;
        this.type = type;
        this.id = gl.createShader(type.id());
    }

    public ShaderType type() {
        return this.type;
    }

    public int id() {
        return this.id;
    }

    public void compile(ForeignString source, ForeignInt success, ForeignCharArray infoLog) throws CompileException {
        gl.shaderSource(id, 1, source.address(), MemoryAddress.NULL);
        gl.compileShader(id);
        gl.getShaderiv(id, GL_COMPILE_STATUS, success.address());
        if (success.get() == GL_FALSE) {
            gl.getShaderInfoLog(id, infoLog.count(), MemoryAddress.NULL, infoLog.address());
            throw new CompileException(id, type, infoLog.getString());
        }
    }

    @Override
    public void close() throws Exception {
        gl.deleteShader(id);
    }
}
