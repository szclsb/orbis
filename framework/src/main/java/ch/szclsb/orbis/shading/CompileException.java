package ch.szclsb.orbis.shading;

public class CompileException extends Exception {
    private final int shader;
    private final ShaderType type;

    public CompileException(int shader, ShaderType type, String message) {
        super(String.format("Failed to compile %s shader %d: %s", type, shader, message));
        this.shader = shader;
        this.type = type;
    }

    public int getShader() {
        return shader;
    }

    public ShaderType getType() {
        return type;
    }
}
