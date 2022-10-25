package ch.szclsb.orbis.app;

import ch.szclsb.orbis.driver.*;
import ch.szclsb.orbis.driver.foreign.ForeignOrbisNative;
import ch.szclsb.orbis.driver.foreign.Introspector;

import java.lang.foreign.MemorySession;

import static ch.szclsb.orbis.driver.OpenGL.*;

public class Triangle {
    public static void main(String[] args) {
        Introspector.loadLibraries();
        try(var session = MemorySession.openShared()) {
            var orbis = new ForeignOrbisNative(session);
            run(orbis);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void run(IOrbisNative driver) throws Throwable {
        var window = driver.createWindow(640, 480, "Hello World");

        var vertices = new float[] {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f
        };

        var vertexShader = driver.createShader(GL_VERTEX_SHADER);
        driver.compileShader(vertexShader, """
                #version 450 core
                                
                layout (location = 0) in vec3 aPos;
                                
                void main()
                {
                    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
                }
                """, errorMessage -> {
            System.err.printf("Failed to compile vertex shader %d: %s\n", vertexShader, errorMessage);
        });
        var fragmentShader = driver.createShader(GL_FRAGMENT_SHADER);
        driver.compileShader(fragmentShader, """
                #version 450 core
                out vec4 FragColor;
                                
                void main()
                {
                    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);
                }
                """, errorMessage -> {
            System.err.printf("Failed to compile fragment shader %d: %s\n", vertexShader, errorMessage);
        });
        var program = driver.createProgram();
        driver.attachShader(program, vertexShader);
        driver.attachShader(program, fragmentShader);
        driver.linkProgram(program, errorMessage -> {
            System.err.printf("Failed to link program %d: %s\n", program, errorMessage);
        });
        driver.deleteShader(vertexShader);
        driver.deleteShader(fragmentShader);

        var vao = driver.createVertexArray();
        driver.bindVertexArray(vao);

        var vbo = driver.createBuffer();
        driver.bindBuffer(GL_ARRAY_BUFFER, vbo);
        driver.bufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        driver.vertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * 4);  // stride: sizeof(float) = 4
        driver.enableVertexAttribArray(0);

        // render loop
        while (driver.shouldWindowClose(window) == 0) {
            driver.clear(GL_COLOR_BUFFER_BIT);

            driver.useProgram(program);
            driver.bindVertexArray(vao);
            driver.drawArrays(GL_TRIANGLES, 0, 3);

            driver.renderWindow(window);
        }

        driver.shutDown();
    }
}
