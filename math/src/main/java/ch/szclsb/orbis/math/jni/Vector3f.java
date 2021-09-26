package ch.szclsb.orbis.math.jni;

import ch.szclsb.orbis.math.IVector3f;
import ch.szclsb.orbis.math.MathUtils;

public class Vector3f implements IVector3f<Vector3f> {
    private static final int SIZE = 3;
    final float[] data;

    public Vector3f() {
        this.data = new float[2];
    }

    public Vector3f(float x, float y) {
        this.data = new float[] {x, y};
    }

    @Override
    public Vector3f add(Vector3f vector) {
        var result = new Vector3f();
        JniVectorAPI.cAdd(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector3f sub(Vector3f vector) {
        var result = new Vector3f();
        JniVectorAPI.cSub(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector3f mul(Vector3f vector) {
        var result = new Vector3f();
        JniVectorAPI.cMul(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector3f mul(float scalar) {
        var result = new Vector3f();
        JniVectorAPI.cMul(SIZE, result.data, this.data, scalar);
        return result;
    }

    @Override
    public Vector3f cross(Vector3f vector) {
        //TODO jni
        return null;
    }

    @Override
    public float dot(Vector3f vector) {
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
    public float[] toArray() {
        var result = new float[SIZE];
        System.arraycopy(data, 0, result, 0, SIZE);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector3f vec) {
            return JniVectorAPI.cEquals(SIZE, data, vec.data, MathUtils.TOLERANCE);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", data[0], data[1], data[2]);
    }
}
