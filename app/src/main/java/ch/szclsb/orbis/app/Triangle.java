package ch.szclsb.orbis.app;

import ch.szclsb.orbis.*;
import ch.szclsb.orbis.buffer.*;
import ch.szclsb.orbis.driver.foreign.GLFW;
import ch.szclsb.orbis.driver.foreign.OpenGL;
import ch.szclsb.orbis.foreign.*;
import ch.szclsb.orbis.shading.Program;
import ch.szclsb.orbis.shading.Shader;
import ch.szclsb.orbis.shading.ShaderType;
import ch.szclsb.orbis.window.Window;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

import static ch.szclsb.orbis.driver.foreign.OpenGL.*;

public class Triangle extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() throws Exception {
        try (var driverSession = DriverSession.open();
             var memSession = MemorySession.openShared()) {
            Window window;
            try (var windowMemSession = MemorySession.openConfined()) {
                var titleSegment = new ForeignString(windowMemSession, "title");
                window = new Window(720, 480, titleSegment);
            }
            window.makeContextCurrent();
            driverSession.initOpenGL();  // init OpenGL after any? window has context

            var program = new Program();
            try (var shaderMemSession = MemorySession.openConfined();
                 var vertexShader = new Shader(ShaderType.VERTEX);
                 var fragmentShader = new Shader(ShaderType.FRAGMENT)) {
                var success = new ForeignInt(shaderMemSession);
                var infoLog = new ForeignCharArray(shaderMemSession, 512);
                var vertexSource = new ForeignString(shaderMemSession, readResource("vertex_shader.glsl"));
                var fragmentSource = new ForeignString(shaderMemSession, readResource("fragment_shader.glsl"));
                vertexShader.compile(vertexSource, success, infoLog);
                fragmentShader.compile(fragmentSource, success, infoLog);
                program.attachShader(vertexShader);
                program.attachShader(fragmentShader);
                program.link(success, infoLog);
            }

            program.use();

//        var vertexLayout = new VertexLayout(new VertexAttribute(ValueType.FLOAT, 3));
            VertexArrayObject vao;
            try (var bufferMemSession = MemorySession.openConfined()) {
                var oneArraySeg = new ForeignIntArray(bufferMemSession, 1);
                var vertices = new ForeignFloatArray(bufferMemSession,
                        0.5f, 0.5f, 0.0f,  // top right
                        0.5f, -0.5f, 0.0f,  // bottom right
                        -0.5f, -0.5f, 0.0f,  // bottom left
                        -0.5f, 0.5f, 0.0f   // top left
                );
                // should be unsigned int?
                var indices = new ForeignIntArray(bufferMemSession,
                        0, 1, 3,   // first triangle
                        1, 2, 3    // second triangle
                );

                vao = VertexArrayObject.create(oneArraySeg).findFirst().orElseThrow();
                vao.bind();

                var vbo = VertexBufferObject.create(oneArraySeg).findFirst().orElseThrow();
                vbo.bind();
                vbo.setData(vertices, DrawingMode.STATIC_DRAW);               // set pos vertex in vbo for vertex shader

                var ebo = ElementBufferObject.create(oneArraySeg).findFirst().orElseThrow();
                ebo.bind();
                ebo.setData(indices, DrawingMode.STATIC_DRAW);

                vbo.init(0, 3, ValueType.FLOAT, false);  // vao and vbo must be bound, attribute dont need to be enabled. This defines pos (location = 0) in vertex shader
                vao.enableAttribute(0);  // attribute are disabled by default, this enables pos (location = 0) in vertex shader
            }

            // render loop
            while (!window.shouldClose()) {
                OpenGL.clear(GL_COLOR_BUFFER_BIT);

//              program.use();
                vao.bind();
//                OpenGL.drawArrays(GL_TRIANGLES, 0, 3);
                OpenGL.drawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, MemoryAddress.NULL);
                OpenGL.bindVertexArray(0);

                window.swapBuffer();
                GLFW.pollEvents();
            }
        }
    }
}
