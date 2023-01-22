package ch.szclsb.orbis;

import ch.szclsb.orbis.driver.foreign.OpenGL;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_COMPILE_STATUS;
import static ch.szclsb.orbis.driver.foreign.OpenGL.GL_FALSE;

public class Shader implements AutoCloseable {
    private static int bufSize = 512;

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

    public void compile(MemorySession session, String source) throws CompileException {
        var success = session.allocate(ValueLayout.JAVA_INT);
        var infoLog = session.allocateArray(ValueLayout.JAVA_CHAR, bufSize);
        var segment = session.allocateUtf8String(source);
        gl.shaderSource(id, 1, segment.address(), MemoryAddress.NULL);
        gl.compileShader(id);
        gl.getShaderiv(id, GL_COMPILE_STATUS, success.address());
        if (success.get(ValueLayout.JAVA_INT, 0) == GL_FALSE) {
            gl.getShaderInfoLog(id, bufSize, MemoryAddress.NULL, infoLog.address());
            throw new CompileException(id, type, infoLog.getUtf8String(0));
        }
    }

    @Override
    public void close() throws Exception {
        gl.deleteShader(id);
    }
}
