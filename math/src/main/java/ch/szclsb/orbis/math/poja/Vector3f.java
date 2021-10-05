package ch.szclsb.orbis.math.poja;

import ch.szclsb.orbis.math.IVector3f;
import ch.szclsb.orbis.math.MathUtils;

import java.util.Formatter;

public class Vector3f implements IVector3f<Vector3f> {
    public static final int SIZE = 3;
    
    public static final Vector3f ZERO = new Vector3f(0f, 0f, 0f);
    public static final Vector3f ONE = new Vector3f(1f, 1f, 1f);
    public static final Vector3f XY = new Vector3f(1f, 1f, 0f);
    public static final Vector3f XZ = new Vector3f(1f, 0f, 1f);
    public static final Vector3f YZ = new Vector3f(0f, 1f, 1f);
    public static final Vector3f X = new Vector3f(1f, 0f, 0f);
    public static final Vector3f Y = new Vector3f(0f, 1f, 0f);
    public static final Vector3f Z = new Vector3f(0f, 0f, 1f);

    public final float[] data;

    public Vector3f(float x, float y, float z) {
        this.data = new float[]{x, y, z};
    }

    public Vector3f() {
        this.data = new float[SIZE];
    }

    public Vector3f(Vector3f a) {
        this();
        System.arraycopy(a.data, 0, this.data, 0, SIZE);
    }

    @Override
    public Vector3f add(Vector3f vec) {
        var result = new Vector3f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] + vec.data[i];
        }
        return result;
    }

    @Override
    public Vector3f sub(Vector3f vec) {
        var result = new Vector3f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] - vec.data[i];
        }
        return result;
    }

    @Override
    public Vector3f times(Vector3f vec) {
        var result = new Vector3f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] * vec.data[i];
        }
        return result;
    }

    @Override
    public Vector3f times(float s) {
        var result = new Vector3f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] * s;
        }
        return result;
    }

    @Override
    public Vector3f cross(Vector3f vector) {
        return null;
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
    public float dot(Vector3f vec) {
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
