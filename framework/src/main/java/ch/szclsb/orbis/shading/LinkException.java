package ch.szclsb.orbis.shading;

public class LinkException extends Exception {
    private final int program;

    public LinkException(int program, String message) {
        super(String.format("Failed to link program %d: %s", program, message));
        this.program = program;
    }

    public int getProgram() {
        return program;
    }
}
