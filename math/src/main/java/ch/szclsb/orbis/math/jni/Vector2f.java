package ch.szclsb.orbis.math.jni;

import ch.szclsb.orbis.math.IVector2f;
import ch.szclsb.orbis.math.MathUtils;

public class Vector2f implements IVector2f<Vector2f> {
    private static final int SIZE = 2;
    private final float[] data;

    public Vector2f() {
        this.data = new float[2];
    }

    public Vector2f(float x, float y) {
        this.data = new float[] {x, y};
    }

    Vector2f(float[] data) {
        this.data = new float[SIZE];
        System.arraycopy(data, 0, this.data, 0, SIZE);
    }

    @Override
    public Vector2f add(Vector2f vector) {
        var result = new Vector2f();
        JniVectorAPI.cAdd(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector2f sub(Vector2f vector) {
        var result = new Vector2f();
        JniVectorAPI.cSub(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector2f times(Vector2f vector) {
        var result = new Vector2f();
        JniVectorAPI.cMul(SIZE, result.data, this.data, vector.data);
        return result;
    }

    @Override
    public Vector2f times(float scalar) {
        var result = new Vector2f();
        JniVectorAPI.cMul(SIZE, result.data, this.data, scalar);
        return result;
    }

    @Override
    public float dot(Vector2f vector) {
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
        if (obj instanceof Vector2f vec) {
            return JniVectorAPI.cEquals(SIZE, data, vec.data, MathUtils.TOLERANCE);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f)", data[0], data[1]);
    }
}
