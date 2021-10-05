package ch.szclsb.orbis.math.poja;

import ch.szclsb.orbis.math.IVector3f;
import ch.szclsb.orbis.math.IVector4f;
import ch.szclsb.orbis.math.MathUtils;
import ch.szclsb.orbis.math.pojo.Vector3f;

import java.util.Formatter;

public class Vector4f implements IVector4f<Vector4f> {
    public static final int SIZE = 4;
    
    public static final Vector4f ZERO = new Vector4f(0f, 0f, 0f, 0f);
    public static final Vector4f ONE = new Vector4f(1f, 1f, 1f, 1f);

    public final float[] data;

    public Vector4f(float x, float y, float z, float w) {
        this.data = new float[]{x, y, z, w};
    }

    public Vector4f() {
        this.data = new float[SIZE];
    }

    public Vector4f(Vector4f a) {
        this();
        System.arraycopy(a.data, 0, this.data, 0, SIZE);
    }

    @Override
    public Vector4f add(Vector4f vec) {
        var result = new Vector4f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] + vec.data[i];
        }
        return result;
    }

    @Override
    public Vector4f sub(Vector4f vec) {
        var result = new Vector4f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] - vec.data[i];
        }
        return result;
    }

    @Override
    public Vector4f times(Vector4f vec) {
        var result = new Vector4f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = this.data[i] * vec.data[i];
        }
        return result;
    }

    @Override
    public Vector4f times(float s) {
        var result = new Vector4f();
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
    public float dot(Vector4f vec) {
        var result = 0f;
        for (var i = 0; i < SIZE; i++) {
            result += this.data[i] * vec.data[i];
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector4f vec) {
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

    @Override
    @SuppressWarnings("unchecked")
    public Vector3f persDiv() {
        float dw = 1f / data[3];
        return new Vector3f(data[0] * dw, data[1] * dw, data[2] * dw);
    }
}
