package ch.szclsb.orbis.math.jni;

import ch.szclsb.orbis.math.IVector4f;
import ch.szclsb.orbis.math.MathUtils;

public class Vector4f implements IVector4f<Vector4f> {
    private static final int SIZE = 4;
    private final float[] data;

    public Vector4f() {
        this.data = new float[SIZE];
    }

    public Vector4f(float x, float y, float z, float w) {
        this.data = new float[] {x, y, z, w};
    }

    @Override
    public Vector4f add(Vector4f vector) {
        var result = new Vector4f();
        JniVectorAPI.cAdd(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector4f sub(Vector4f vector) {
        var result = new Vector4f();
        JniVectorAPI.cSub(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector4f times(Vector4f vector) {
        var result = new Vector4f();
        JniVectorAPI.cMul(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector4f times(float scalar) {
        var result = new Vector4f();
        JniVectorAPI.cMul(SIZE, result.data, this.data, scalar);
        return result;
    }

    @Override
    public float dot(Vector4f vector) {
        return JniVectorAPI.cDot(SIZE, this.data, vector.data);
    }

    @Override
    public float normSquared() {
        return JniVectorAPI.cDot(SIZE, this.data, this.data);
    }

    @Override
    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Vector3f persDiv() {
        var result = new Vector3f();
        JniVectorAPI.cMul(3, result.data, this.data, 1f / data[3]);
        return result;
    }

    @Override
    public float[] toArray() {
        var result = new float[SIZE];
        System.arraycopy(data, 0, result, 0, SIZE);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector4f vec) {
            return JniVectorAPI.cEquals(SIZE, data, vec.data, MathUtils.TOLERANCE);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f, %.3f)", data[0], data[1], data[2], data[3]);
    }
}
