package ch.szclsb.orbis.app;

import ch.szclsb.orbis.Application;
import ch.szclsb.orbis.Program;
import ch.szclsb.orbis.Shader;
import ch.szclsb.orbis.ShaderType;
import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.driver.foreign.OpenGL;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;

public class Triangle extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run(MemorySession session, GLFW glfw, OpenGL gl) throws Exception {
        if (glfw.init() == 0) {
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

        var program = new Program(gl);
        try (var vertexShader = new Shader(gl, ShaderType.VERTEX);
             var fragmentShader = new Shader(gl, ShaderType.FRAGMENT)) {
            vertexShader.compile(session, """
                #version 450 core
                                
                layout (location = 0) in vec3 aPos;
                                
                void main()
                {
                    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
                }
                """);
            fragmentShader.compile(session, """
                #version 450 core
                out vec4 FragColor;
                                
                void main()
                {
                    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);
                }
                """);
            program.attachShader(vertexShader);
            program.attachShader(fragmentShader);
            program.link(session);
        }

        var vertices = session.allocateArray(ValueLayout.JAVA_FLOAT,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
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

            program.use();
            gl.bindVertexArray(vao);
            gl.drawArrays(GL_TRIANGLES, 0, 3);

            glfw.swapBuffers(window);
            glfw.pollEvents();
        }

        glfw.shutDown();
    }
}
