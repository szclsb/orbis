package ch.szclsb.orbis.app;

import ch.szclsb.orbis.*;
import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.ForeignCharArray;
import ch.szclsb.orbis.foreign.ForeignInt;
import ch.szclsb.orbis.foreign.ForeignString;

import java.io.*;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

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
        var titleSegment = new ForeignString(session, "Hello World");
        var window = new Window(glfw, 640, 480, titleSegment);
        window.makeCurrent();
        if (gl.load() == 0) {
            return;
        }

        var program = new Program(gl);
        try (var shaderSession = MemorySession.openConfined();
            var vertexShader = new Shader(gl, ShaderType.VERTEX);
            var fragmentShader = new Shader(gl, ShaderType.FRAGMENT)) {
            var success = new ForeignInt(shaderSession);
            var infoLog = new ForeignCharArray(shaderSession, 512);
            var vertexSource = new ForeignString(shaderSession, readResource("vertex_shader.glsl"));
            var fragmentSource = new ForeignString(shaderSession, readResource("fragment_shader.glsl"));
            vertexShader.compile(vertexSource, success, infoLog);
            fragmentShader.compile(fragmentSource, success, infoLog);
            program.attachShader(vertexShader);
            program.attachShader(fragmentShader);
            program.link(success, infoLog);
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
        while (!window.shouldClose()) {
            gl.clear(GL_COLOR_BUFFER_BIT);

            program.use();
            gl.bindVertexArray(vao);
            gl.drawArrays(GL_TRIANGLES, 0, 3);

            window.swapBuffer();
            glfw.pollEvents();
        }

        glfw.shutDown();
    }
}
