package ch.szclsb.orbis.app;

import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.driver.foreign.Introspector;
import ch.szclsb.orbis.driver.foreign.OpenGL;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;

public class Triangle {
    public static void main(String[] args) {
        Introspector.loadLibraries();
        try(var session = MemorySession.openShared()) {
            var glfw = new GLFW();
            var gl = new OpenGL();
            run(session, glfw, gl);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void run(MemorySession session, GLFW glfw, OpenGL gl) throws Throwable {
        if(glfw.init() == 0) {
            return;
        }
        var titleSegment = session.allocateUtf8String("Hello World");
        var window = glfw.createWindow(640, 480, titleSegment.address(), MemoryAddress.NULL, MemoryAddress.NULL);
        if (window == MemoryAddress.NULL) {
            return;
        }
        glfw.makeContextCurrent(window);
        if (gl.load() == 0) {
            return;
        }

        var success = session.allocate(ValueLayout.JAVA_INT);
        var infoLog = session.allocateArray(ValueLayout.JAVA_CHAR, 512);
        var vertexShader = gl.createShader(GL_VERTEX_SHADER);
        var vertexSourceSegment = session.allocateUtf8String("""
                #version 450 core
                                
                layout (location = 0) in vec3 aPos;
                                
                void main()
                {
                    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
                }
                """);
        gl.shaderSource(vertexShader, 1, vertexSourceSegment.address(), MemoryAddress.NULL);
        gl.compileShader(vertexShader);
        gl.getShaderiv(vertexShader, GL_COMPILE_STATUS, success.address());
        if (success.get(ValueLayout.JAVA_INT, 0) == GL_FALSE) {
            gl.getShaderInfoLog(vertexShader, 512, MemoryAddress.NULL, infoLog.address());
            System.err.printf("Failed to compile vertex shader %d: %s%n", vertexShader, infoLog.getUtf8String(0));
            return;
        }

        var fragmentShader = gl.createShader(GL_FRAGMENT_SHADER);
        var fragmentSourceSegment = session.allocateUtf8String("""
                #version 450 core
                out vec4 FragColor;
                                
                void main()
                {
                    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);
                }
                """);
        gl.shaderSource(fragmentShader, 1, fragmentSourceSegment.address(), MemoryAddress.NULL);
        gl.compileShader(fragmentShader);
        gl.getShaderiv(fragmentShader, GL_COMPILE_STATUS, success.address());
        if (success.get(ValueLayout.JAVA_INT, 0) == GL_FALSE) {
            gl.getShaderInfoLog(fragmentShader, 512, MemoryAddress.NULL, infoLog.address());
            System.err.printf("Failed to compile fragment shader %d: %s%n", fragmentShader, infoLog.getUtf8String(0));
            return;
        }

        var program = gl.createProgram();
        gl.attachShader(program, vertexShader);
        gl.attachShader(program, fragmentShader);
        gl.linkProgram(program);
        gl.deleteShader(vertexShader);
        gl.deleteShader(fragmentShader);
        gl.getProgramiv(program, GL_LINK_STATUS, success.address());
        if (success.get(ValueLayout.JAVA_INT, 0) == GL_FALSE) {
            gl.getProgramInfoLog(program, 512, MemoryAddress.NULL, infoLog.address());
            System.err.printf("Failed to link program %d: %s\n", program, infoLog.getUtf8String(0));
            return;
        }

        var vertices = session.allocateArray(ValueLayout.JAVA_FLOAT,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f
        );

        var arraySegment = session.allocate(ValueLayout.JAVA_INT);
        gl.createVertexArrays(1, arraySegment.address());
        var vao = arraySegment.get(ValueLayout.JAVA_INT, 0);
        gl.bindVertexArray(vao);

        var bufferSegment = session.allocate(ValueLayout.JAVA_INT);
        gl.createBuffers(1, bufferSegment.address());
        var vbo = bufferSegment.get(ValueLayout.JAVA_INT, 0);
        gl.bindBuffer(GL_ARRAY_BUFFER, vbo);
        gl.bufferData(GL_ARRAY_BUFFER, vertices.byteSize(), vertices.address(), GL_STATIC_DRAW);
        gl.vertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * 4, MemoryAddress.NULL);  // stride: sizeof(float) = 4
        gl.enableVertexAttribArray(0);

        // render loop
        while (glfw.windowShouldClose(window) == 0) {
            gl.clear(GL_COLOR_BUFFER_BIT);

            gl.useProgram(program);
            gl.bindVertexArray(vao);
            gl.drawArrays(GL_TRIANGLES, 0, 3);

            glfw.swapBuffers(window);
            glfw.pollEvents();
        }

        glfw.shutDown();
    }
}
