package ch.szclsb.orbis.shading;

import ch.szclsb.orbis.foreign.ForeignCharArray;
import ch.szclsb.orbis.foreign.ForeignInt;
import ch.szclsb.orbis.foreign.ForeignString;

import java.lang.foreign.MemoryAddress;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;

public class Shader implements AutoCloseable {
    private final ShaderType type;
    private final int id;

    public Shader(ShaderType type) {
        this.type = type;
        this.id = createShader(type.id());
    }

    public ShaderType type() {
        return this.type;
    }

    public int id() {
        return this.id;
    }

    public void compile(ForeignString source, ForeignInt success, ForeignCharArray infoLog) throws CompileException {
        shaderSource(id, 1, source.address(), MemoryAddress.NULL);
        compileShader(id);
        getShaderiv(id, GL_COMPILE_STATUS, success.address());
        if (success.get() == GL_FALSE) {
            getShaderInfoLog(id, infoLog.count(), MemoryAddress.NULL, infoLog.address());
            throw new CompileException(id, type, infoLog.getString());
        }
    }

    @Override
    public void close() throws Exception {
        deleteShader(id);
    }
}
