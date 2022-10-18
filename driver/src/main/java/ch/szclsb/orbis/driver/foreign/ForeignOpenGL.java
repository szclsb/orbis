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

    private final MethodHandle glGenVertexArrays;
    private final MethodHandle glBindVertexArray;
    private final MethodHandle glGenBuffers;
    private final MethodHandle glIsBuffer;
    private final MethodHandle glVertexAttribPointer;
    private final MethodHandle glEnableVertexAttribArray;
    private final MethodHandle glBindBuffer;
    private final MethodHandle glBufferData;
    private final MethodHandle glBufferSubData;
    private final MethodHandle glDeleteBuffers;

    private final MethodHandle glGetUniformLocation;
    private final MethodHandle glUniform1i;
    private final MethodHandle glUniform1iv;
    private final MethodHandle glUniform2iv;
    private final MethodHandle glUniform3iv;
    private final MethodHandle glUniform4iv;
    private final MethodHandle glUniform1f;
    private final MethodHandle glUniform1fv;
    private final MethodHandle glUniform2fv;
    private final MethodHandle glUniform3fv;
    private final MethodHandle glUniform4fv;
    private final MethodHandle glUniformMatrix2fv;
    private final MethodHandle glUniformMatrix3fv;
    private final MethodHandle glUniformMatrix4fv;

    private final MethodHandle glClear;

    public ForeignOpenGL() {

        this.glCreateShader             = loadMethod("__glewCreateShader", JAVA_INT, JAVA_INT);
        this.glShaderSource             = loadMethod("__glewShaderSource", null, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
        this.glCompileShader            = loadMethod("__glewCompileShader", null, JAVA_INT);
        this.glGetShaderiv              = loadMethod("__glewGetShaderiv", null, JAVA_INT, JAVA_INT, ADDRESS);
        this.glGetShaderInfoLog         = loadMethod("__glewGetShaderInfoLog", null, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
        this.glDeleteShader             = loadMethod("__glewDeleteShader", null, JAVA_INT);

        this.glCreateProgram            = loadMethod("__glewCreateProgram", JAVA_INT);
        this.glAttachShader             = loadMethod("__glewAttachShader", null, JAVA_INT, JAVA_INT);
        this.glLinkProgram              = loadMethod("__glewLinkProgram", null, JAVA_INT);
        this.glGetProgramiv             = loadMethod("__glewGetProgramiv", null, JAVA_INT, JAVA_INT, ADDRESS);
        this.glGetProgramInfoLog        = loadMethod("__glewGetProgramInfoLog", null, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
        this.glUseProgram               = loadMethod("__glewUseProgram", null, JAVA_INT);

        this.glGenVertexArrays          = loadMethod("__glewGenVertexArrays", null, JAVA_INT, ADDRESS);
        this.glBindVertexArray          = loadMethod("__glewBindVertexArray", null, JAVA_INT);
        this.glGenBuffers               = loadMethod("__glewGenBuffers", null, JAVA_INT, ADDRESS);
        this.glBindBuffer               = loadMethod("__glewBindBuffer", null, JAVA_INT, JAVA_INT);
        this.glIsBuffer                 = loadMethod("__glewIsBuffer", JAVA_INT, JAVA_INT);
        this.glVertexAttribPointer      = loadMethod("__glewVertexAttribPointer", null, JAVA_INT, JAVA_INT, JAVA_INT, JAVA_INT, JAVA_INT, ADDRESS);
        this.glEnableVertexAttribArray  = loadMethod("__glewEnableVertexAttribArray", null, JAVA_INT);
        this.glBufferData               = loadMethod("__glewBufferData", null, JAVA_INT, JAVA_INT, ADDRESS, JAVA_INT);
        this.glBufferSubData            = loadMethod("__glewBufferSubData", null, JAVA_INT, JAVA_INT, JAVA_INT, ADDRESS);
        this.glDeleteBuffers            = loadMethod("__glewDeleteBuffers", null, JAVA_INT, ADDRESS);

        this.glGetUniformLocation       = loadMethod("__glewGetUniformLocation", JAVA_INT, JAVA_INT, ADDRESS);
        this.glUniform1i               = loadMethod("__glewUniform1i", null, JAVA_INT, JAVA_INT);
        this.glUniform1iv               = loadMethod("__glewUniform1iv", null, JAVA_INT, JAVA_INT, ADDRESS);
        this.glUniform2iv               = loadMethod("__glewUniform2iv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniform3iv               = loadMethod("__glewUniform3iv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniform4iv               = loadMethod("__glewUniform4iv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniform1f               = loadMethod("__glewUniform1f", null, JAVA_INT, JAVA_FLOAT);;
        this.glUniform1fv               = loadMethod("__glewUniform1fv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniform2fv               = loadMethod("__glewUniform2fv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniform3fv               = loadMethod("__glewUniform3fv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniform4fv               = loadMethod("__glewUniform4fv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniformMatrix2fv         = loadMethod("__glewUniformMatrix2fv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniformMatrix3fv         = loadMethod("__glewUniformMatrix3fv", null, JAVA_INT, JAVA_INT, ADDRESS);;
        this.glUniformMatrix4fv         = loadMethod("__glewUniformMatrix4fv", null, JAVA_INT, JAVA_INT, ADDRESS);;

        this.glClear                   = loadMethod("glClear", null, JAVA_INT);
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

    @Override
    public void generateVertexArrays(int number, MemoryAddress array) throws Throwable {
        glGenVertexArrays.invoke(number, array);
    }

    @Override
    public void bindVertexArray(int vao) throws Throwable {
        glBindVertexArray.invoke(vao);
    }

    @Override
    public void generateBuffers(int numbers, MemoryAddress array) throws Throwable {
        glGenBuffers.invoke(numbers, array);
    }

    @Override
    public void bindBuffers(int target, int vbo) throws Throwable {
        glBindBuffer.invoke(vbo);
    }

    @Override
    public int isBuffer(int vbo) throws Throwable {
        return (int) glIsBuffer.invoke(vbo);
    }

    @Override
    public void vertexAttribPointer(int index, int size, int type, int normalized, int stride, MemoryAddress pointer) throws Throwable {
        glVertexAttribPointer.invoke(index, size, type, normalized, stride, pointer);
    }

    @Override
    public void enableVertexAttribArray(int index) throws Throwable {
        glEnableVertexAttribArray.invoke(index);
    }

    @Override
    public void bufferData(int target, int size, MemoryAddress data, int mode) throws Throwable {
        glBufferData.invoke(target, size, data, mode);
    }

    @Override
    public void bufferSubData(int target, int offset, int size, MemoryAddress data) throws Throwable {
        glBufferSubData.invoke(target, offset, size, data);
    }

    @Override
    public void deleteBuffers(int n, MemoryAddress buffers) throws Throwable {
        glDeleteBuffers.invoke(n, buffers);
    }

    @Override
    public int getUniformLocation(int program, MemoryAddress name) throws Throwable {
        return (int) glGetUniformLocation.invoke(program, name);
    }

    @Override
    public void uniform1i(int location, int value) throws Throwable {
        glUniform1i.invoke(location, value);
    }

    @Override
    public void uniform1iv(int location, int size, MemoryAddress value) throws Throwable {
        glUniform1iv.invoke(location, size, value);
    }

    @Override
    public void uniform2iv(int location, int size, MemoryAddress value) throws Throwable {
        glUniform2iv.invoke(location, size, value);
    }

    @Override
    public void uniform3iv(int location, int size, MemoryAddress value) throws Throwable {
        glUniform3iv.invoke(location, size, value);
    }

    @Override
    public void uniform4iv(int location, int size, MemoryAddress value) throws Throwable {
        glUniform4iv.invoke(location, size, value);
    }

    @Override
    public void uniform1f(int location, float value) throws Throwable {
        glUniform1f.invoke(location, value);
    }

    @Override
    public void uniform1fv(int location, int size, MemoryAddress value) throws Throwable {
        glUniform1fv.invoke(location, size, value);
    }

    @Override
    public void uniform2fv(int location, int size, MemoryAddress value) throws Throwable {
        glUniform2fv.invoke(location, size, value);
    }

    @Override
    public void uniform3fv(int location, int size, MemoryAddress value) throws Throwable {
        glUniform3fv.invoke(location, size, value);
    }

    @Override
    public void uniform4fv(int location, int size, MemoryAddress value) throws Throwable {
        glUniform4fv.invoke(location, size, value);
    }

    @Override
    public void uniformMatrix2fv(int location, int size, MemoryAddress value) throws Throwable {
        glUniformMatrix2fv.invoke(location, size, value);
    }

    @Override
    public void uniformMatrix3fv(int location, int size, MemoryAddress value) throws Throwable {
        glUniformMatrix2fv.invoke(location, size, value);
    }

    @Override
    public void uniformMatrix4fv(int location, int size, MemoryAddress value) throws Throwable {
        glUniformMatrix4fv.invoke(location, size, value);
    }
}
