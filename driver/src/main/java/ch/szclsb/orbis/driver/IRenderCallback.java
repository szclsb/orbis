package ch.szclsb.orbis.driver;

@FunctionalInterface
public interface IRenderCallback {
    void render(double time) throws Throwable;
}
