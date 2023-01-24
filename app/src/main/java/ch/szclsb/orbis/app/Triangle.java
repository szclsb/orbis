package ch.szclsb.orbis.app;

import ch.szclsb.orbis.*;
import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.*;

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

        VertexArray vao;
        ArrayBuffer vbo;
        try (var bufferSession = MemorySession.openConfined()) {
            var arraySegment = new ForeignIntArray(bufferSession, 1);
            var vertices = new ForeignFloatArray(bufferSession,
                    -0.5f, -0.5f, 0.0f,
                    0.5f, -0.5f, 0.0f,
                    0.0f, 0.5f, 0.0f
            );

            vao = VertexArray.create(gl, arraySegment).findFirst().orElseThrow();
            vao.bind();
            vbo = ArrayBuffer.create(gl, arraySegment).findFirst().orElseThrow();
            vbo.bind();
            vbo.init(ValueType.FLOAT, 3, false);
            vbo.setData(vertices, DrawingMode.STATIC_DRAW);
        }

        // render loop
        while (!window.shouldClose()) {
            gl.clear(GL_COLOR_BUFFER_BIT);

            program.use();
            vao.bind();
            gl.drawArrays(GL_TRIANGLES, 0, 3);

            window.swapBuffer();
            glfw.pollEvents();
        }

        glfw.shutDown();
    }
}
