package ch.szclsb.orbis.driver.foreign;

import ch.szclsb.orbis.driver.OpenGL;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

import static java.lang.foreign.ValueLayout.*;
import static ch.szclsb.orbis.driver.foreign.Introspector.loadMethod;

public class ForeignOpenGL implements OpenGL {
    private final MethodHandle glCreateShader;
    private final MethodHandle glShaderSource;
    private final MethodHandle glCompileShader;
    private final MethodHandle glGetShaderiv;
    private final MethodHandle glGetShaderInfoLog;
    private final MethodHandle glDeleteShader;

    private final MethodHandle glCreateProgram;
    private final MethodHandle glGetProgramiv;
    private final MethodHandle glGetProgramInfoLog;
    private final MethodHandle glAttachShader;
    private final MethodHandle glLinkProgram;
    private final MethodHandle glUseProgram;

//    private final MethodHandle glGenVertexArrays;
//    private final MethodHandle glBindVertexArray;
//    private final MethodHandle glGenBuffers;
//    private final MethodHandle glIsBuffer;
//    private final MethodHandle glVertexAttribPointer;
//    private final MethodHandle glEnableVertexAttribArray;
//    private final MethodHandle glBindBuffer;
//    private final MethodHandle glBufferData;
//    private final MethodHandle glBufferSubData;
//    private final MethodHandle glGetBufferSubData;
//    private final MethodHandle glDeleteBuffers;

//    private final MethodHandle getUniformLocation;
//    private final MethodHandle glUniform1iv;
//    private final MethodHandle glUniform2iv;
//    private final MethodHandle glUniform3iv;
//    private final MethodHandle glUniform4iv;
//    private final MethodHandle glUniform1fv;
//    private final MethodHandle glUniform2fv;
//    private final MethodHandle glUniform3fv;
//    private final MethodHandle glUniform4fv;
//    private final MethodHandle glUniformMatrix2fv;
//    private final MethodHandle glUniformMatrix3fv;
//    private final MethodHandle glUniformMatrix4fv;

    private final MethodHandle glClear;

    public ForeignOpenGL() {

        this.glCreateShader         = loadMethod("__glewCreateShader", JAVA_INT, JAVA_INT);
        this.glShaderSource         = loadMethod("__glewShaderSource", null, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
        this.glCompileShader        = loadMethod("__glewCompileShader", null, JAVA_INT);
        this.glGetShaderiv          = loadMethod("__glewGetShaderiv", null, JAVA_INT, JAVA_INT, ADDRESS);
        this.glGetShaderInfoLog     = loadMethod("__glewGetShaderInfoLog", null, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
        this.glDeleteShader         = loadMethod("__glewDeleteShader", null, JAVA_INT);

        this.glCreateProgram        = loadMethod("__glewCreateProgram", JAVA_INT);
        this.glAttachShader         = loadMethod("__glewAttachShader", null, JAVA_INT, JAVA_INT);
        this.glLinkProgram          = loadMethod("__glewLinkProgram", null, JAVA_INT);
        this.glGetProgramiv         = loadMethod("__glewGetProgramiv", null, JAVA_INT, JAVA_INT, ADDRESS);
        this.glGetProgramInfoLog    = loadMethod("__glewGetProgramInfoLog", null, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
        this.glUseProgram           = loadMethod("__glewUseProgram", null, JAVA_INT);

        this.glClear                = loadMethod("glClear", null, JAVA_INT);
    }

    @Override
    public int createShader(int shaderType) throws Throwable {
        return (int) glCreateShader.invoke(shaderType);
    }

    @Override
    public void shaderSource(int shader, MemorySegment source) throws Throwable {
        glShaderSource.invoke(shader, 1, source.address(), MemoryAddress.NULL);
    }

    @Override
    public void compileShader(int shader) throws Throwable {
        glCompileShader.invoke(shader);
    }

    @Override
    public void getShaderiv(int shader, int pname, MemoryAddress success) throws Throwable {
        glGetShaderiv.invoke(shader, pname, success);
    }

    @Override
    public void getShaderInfoLog(int shader, int maxLength, MemoryAddress length, MemoryAddress infoLog) throws Throwable {
        glGetShaderInfoLog.invoke(shader, maxLength, length, infoLog);
    }

    @Override
    public void deleteShader(int shader) throws Throwable {
        glDeleteShader.invoke(shader);
    }

    @Override
    public int createProgram() throws Throwable {
        return (int) glCreateProgram.invoke();
    }

    @Override
    public void attachShader(int program, int shader) throws Throwable {
        glAttachShader.invoke(program, shader);
    }

    @Override
    public void linkProgram(int program) throws Throwable {
        glLinkProgram.invoke(program);
    }

    @Override
    public void getProgramIv(int program, int pname, MemoryAddress success) throws Throwable {
        glGetProgramiv.invoke(program, pname, success);
    }

    @Override
    public void getProgramInfoLog(int program, int maxlength, MemoryAddress length, MemoryAddress infoLog) throws Throwable {
        glGetProgramInfoLog.invoke(program, maxlength, length, infoLog);
    }

    @Override
    public void useProgram(int program) throws Throwable {
        glUseProgram.invoke(program);
    }



    @Override
    public void clear(int bitmask) throws Throwable {
        glClear.invoke(bitmask);
    }
}
