package ch.szclsb.orbis.app;

import ch.szclsb.orbis.driver.GLFW;
import ch.szclsb.orbis.driver.OpenGL;
import ch.szclsb.orbis.driver.foreign.ForeignGLFW;
import ch.szclsb.orbis.driver.foreign.ForeignOpenGL;
import ch.szclsb.orbis.driver.foreign.Introspector;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

import static ch.szclsb.orbis.driver.OpenGL.*;

public class Triangle {
    public static void main(String[] args) {
        Introspector.loadLibraries();
        try(var session = MemorySession.openShared()) {
            var glfw = new ForeignGLFW();
            var gl = new ForeignOpenGL();
            run(session, glfw, gl);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void run(MemorySession session, GLFW glfw, OpenGL gl) throws Throwable {
        if (glfw.init() == GL_FALSE || gl.init() == GL_FALSE) {
            glfw.terminate();
            return;
        }

        var title = session.allocateUtf8String("Hello World");
        var window = glfw.createWindow(640, 480, title);
        if (window == MemoryAddress.NULL) {
            glfw.terminate();
            return;
        }
        glfw.makeContextCurrent(window);

        var vertices = session.allocateArray(ValueLayout.JAVA_FLOAT,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f);

        var success = session.allocate(ValueLayout.JAVA_INT);
        var vertexShader = gl.createShader(GL_VERTEX_SHADER);
        gl.shaderSource(vertexShader, session.allocateUtf8String("""
                #version 330 core
                                
                layout (location = 0) in vec3 aPos;
                                
                void main()
                {
                    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
                }
                """));
        gl.compileShader(vertexShader);
        gl.getShaderiv(vertexShader, GL_COMPILE_STATUS, success.address());
        if (success.get(ValueLayout.JAVA_INT, 0) == GL_FALSE) {
            var log = session.allocateArray(ValueLayout.JAVA_CHAR, 512);
            var logLength = session.allocate(ValueLayout.JAVA_INT);
            gl.getShaderInfoLog(vertexShader, 512, logLength.address(), log.address());
            var logMessage = log.getUtf8String(0);
            System.err.printf("Failed to compile vertex shader %d: %s\n", vertexShader, logMessage);
        }
        var fragmentShader = gl.createShader(GL_FRAGMENT_SHADER);
        gl.shaderSource(fragmentShader, session.allocateUtf8String("""
                #version 330 core
                out vec4 FragColor;
                                
                void main()
                {
                    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);
                }
                """));
        gl.compileShader(fragmentShader);
        gl.getShaderiv(fragmentShader, GL_COMPILE_STATUS, success.address());
        if (success.get(ValueLayout.JAVA_INT, 0) == GL_FALSE) {
            var log = session.allocateArray(ValueLayout.JAVA_CHAR, 512);
            var logLength = session.allocate(ValueLayout.JAVA_INT);
            gl.getShaderInfoLog(fragmentShader, 512, logLength.address(), log.address());
            var logMessage = log.getUtf8String(0);
            System.err.printf("Failed to compile fragment shader %d: %s\n", fragmentShader, logMessage);
        }

        var program = gl.createProgram();
        gl.attachShader(program, vertexShader);
        gl.attachShader(program, fragmentShader);
        gl.linkProgram(program);
        gl.getProgramIv(program, GL_LINK_STATUS, success.address());
        if (success.get(ValueLayout.JAVA_INT, 0) == GL_FALSE) {
            var log = session.allocateArray(ValueLayout.JAVA_CHAR, 512);
            var logLength = session.allocate(ValueLayout.JAVA_INT);
            gl.getProgramInfoLog(program, 512, logLength.address(), log.address());
            var logMessage = log.getUtf8String(0);
            System.err.printf("Failed to link program %d: %s\n", program, logMessage);
        }
        gl.deleteShader(vertexShader);
        gl.deleteShader(fragmentShader);

        var vaos = session.allocate(ValueLayout.JAVA_INT);
        gl.generateVertexArrays(1, vaos.address());
        var vao = vaos.get(ValueLayout.JAVA_INT, 0);
        gl.bindVertexArray(vao);

        var vbos = session.allocate(ValueLayout.JAVA_INT);
        gl.generateBuffers(1, vbos.address());
        var vbo = vbos.get(ValueLayout.JAVA_INT, 0);
        gl.bindBuffers(GL_ARRAY_BUFFER, vbo);
        gl.bufferData(GL_ARRAY_BUFFER, (int) vertices.byteSize(), vertices.address(), GL_STATIC_DRAW);
        gl.vertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * 32, MemoryAddress.NULL);
        gl.enableVertexAttribArray(0);

        while (glfw.shouldWindowClose(window) == GL_FALSE) {
            gl.clear(GL_COLOR_BUFFER_BIT);
            gl.useProgram(program);
            gl.bindVertexArray(vao);
            gl.drawArrays(GL_TRIANGLES, 0, 3);

            glfw.swapBuffers(window);
            glfw.pollEvents();
        }
        glfw.terminate();
    }
}
