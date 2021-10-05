package ch.szclsb.orbis.math.poja;

import ch.szclsb.orbis.math.IVector2f;
import ch.szclsb.orbis.math.MathUtils;

import java.util.Formatter;

public class Vector2f implements IVector2f<Vector2f> {
    public static final int SIZE = 2;

    public static final Vector2f ZERO = new Vector2f(0f, 0f);
    public static final Vector2f ONE = new Vector2f(1f, 1f);
    public static final Vector2f X = new Vector2f(1f, 0f);
    public static final Vector2f Y = new Vector2f(0f, 1f);

    public final float[] data;

    public Vector2f(float x, float y) {
        this.data = new float[]{x, y};
    }

    public Vector2f() {
        this.data = new float[SIZE];
    }

    public Vector2f(Vector2f a) {
        this();
        System.arraycopy(a.data, 0, this.data, 0, SIZE);
    }

    @Override
    public Vector2f add(Vector2f vec) {
        var result = new Vector2f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] + vec.data[i];
        }
        return result;
    }

    @Override
    public Vector2f sub(Vector2f vec) {
        var result = new Vector2f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] - vec.data[i];
        }
        return result;
    }

    @Override
    public Vector2f times(Vector2f vec) {
        var result = new Vector2f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] * vec.data[i];
        }
        return result;
    }

    @Override
    public Vector2f times(float s) {
        var result = new Vector2f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] * s;
        }
        return result;
    }

    @Override
    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    @Override
    public float normSquared() {
        return dot(this);
    }

    @Override
    public float dot(Vector2f vec) {
        var result = 0f;
        for (var i = 0; i < SIZE; i++) {
            result += this.data[i] * vec.data[i];
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2f vec) {
            var result = true;
            for (var i = 0; result && i < SIZE; i++) {
                result = MathUtils.isFloatEquals(this.data[i], vec.data[i]);
            }
            return result;
        }
        return false;
    }

    @Override
    public float[] toArray() {
        var result = new float[SIZE];
        System.arraycopy(this.data, 0, result, 0, SIZE);
        return result;
    }

    @Override
    public String toString() {
        //no float stream available, don't use double stream
        var formatter = new Formatter();
        formatter.format("(");
        if (data.length > 0) {
            formatter.format("%.3f", data[0]);
            for (var i = 1; i < SIZE; i++) {
                formatter.format(", %.3f", data[i]);
            }
        }
        formatter.format(")");
        return formatter.toString();
    }
}
