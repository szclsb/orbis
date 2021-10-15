package ch.szclsb.orbis.math;

@Simd(lanes = 4)
public class Vector4f extends Vector {
    public Vector4f(float x, float y, float z, float w) {
        this.data = new float[]{x, y, z, w};
    }
}
