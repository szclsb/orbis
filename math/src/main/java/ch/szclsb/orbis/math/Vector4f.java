package ch.szclsb.orbis.math;

public class Vector4f {
    protected final float[] data;

    public Vector4f(float x, float y, float z, float w) {
        this.data = new float[]{x, y, z, w};
    }
}
