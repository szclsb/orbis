package ch.szclsb.orbis.math.pojo;

import ch.szclsb.orbis.math.IVector2f;
import ch.szclsb.orbis.math.MathUtils;

public class Vector2f implements IVector2f<Vector2f> {
    public static final Vector2f ZERO = new Vector2f(0f, 0f);
    public static final Vector2f ONE = new Vector2f(1f, 1f);
    public static final Vector2f X = new Vector2f(1f, 0f);
    public static final Vector2f Y = new Vector2f(0f, 1f);

    public final float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f a) {
        this.x = a.x;
        this.y = a.y;
    }

    @Override
    public Vector2f add(Vector2f vec) {
        return new Vector2f(x + vec.x, y + vec.y);
    }

    @Override
    public Vector2f sub(Vector2f vec) {
        return new Vector2f(x - vec.x, y - vec.y);
    }

    @Override
    public Vector2f times(Vector2f vec) {
        return new Vector2f(x * vec.x, y * vec.y);
    }

    @Override
    public Vector2f times(float s) {
        return new Vector2f(x * s, y * s);
    }

    @Override
    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    @Override
    public float normSquared() {
        return x * x + y * y;
    }

    @Override
    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2f vec) {
            return MathUtils.isFloatEquals(x, vec.x) && MathUtils.isFloatEquals(y, vec.y);
        }
        return false;
    }

    @Override
    public float[] toArray() {
        return new float[]{x, y};
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f)", x, y);
    }
}
