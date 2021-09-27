package ch.szclsb.orbis.math.pojo;

import ch.szclsb.orbis.math.IMat4f;
import ch.szclsb.orbis.math.IVector4f;
import ch.szclsb.orbis.math.MathUtils;

public class Matrix4f implements IMat4f<Matrix4f> {
    private static final int DIM = 4;
    private static final int SIZE = DIM * DIM;

    private final float[] data;

    public Matrix4f(float a11, float a12, float a13, float a14,
                    float a21, float a22, float a23, float a24,
                    float a31, float a32, float a33, float a34,
                    float a41, float a42, float a43, float a44) {
        this.data = new float[]{
                a11, a12, a13, a14,
                a21, a22, a23, a24,
                a31, a32, a33, a34,
                a41, a42, a43, a44
        };
    }

    public Matrix4f() {
        this.data = new float[SIZE];
    }

    public Matrix4f(float[] data) {
        if (data.length != SIZE) throw new RuntimeException("Invalid Matrix size");
        this.data = new float[SIZE];
        System.arraycopy(data, 0, this.data, 0, SIZE);
    }

    public Matrix4f(Matrix4f mat) {
        this.data = new float[SIZE];
        System.arraycopy(mat.data, 0, this.data, 0, SIZE);
    }

    @Override
    public Matrix4f add(Matrix4f mat) {
        var result = new Matrix4f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = data[i] + mat.data[i];
        }
        return result;
    }

    @Override
    public Matrix4f sub(Matrix4f mat) {
        var result = new Matrix4f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = data[i] - mat.data[i];
        }
        return result;
    }

    @Override
    public Matrix4f times(Matrix4f mat) {
        return times(mat, false);
    }

    @Override
    public Matrix4f times(Matrix4f mat, boolean elementwise) {
        var result = new Matrix4f();
        if (!elementwise) {
            for (var i = 0; i < DIM; i++) {
                for (var j = 0; j < DIM; j++) {
                    var sum = 0;
                    for (var k = 0; k < DIM; k++) {
                        sum += data[i * DIM + k] * mat.data[k * DIM + j];
                    }
                    result.data[i * DIM + j] = sum;
                }
            }
        } else {
            for (var i = 0; i < SIZE; i++) {
                result.data[i] = data[i] * mat.data[i];
            }
        }
        return result;
    }

    @Override
    public Matrix4f times(float scalar) {
        var result = new Matrix4f();
        for (var i = 0; i < SIZE; i++) {
            result.data[i] = data[i] * scalar;
        }
        return result;
    }

    @Override
    public float[] toArray() {
        var buffer = new float[SIZE];
        System.arraycopy(data, 0, buffer, 0, SIZE);
        return buffer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Matrix4f mat) {
            var result = true;
            for(var i = 0; result && i < SIZE; i++) {
                result = MathUtils.isFloatEquals(data[i], mat.data[i]);
            }
            return result;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("""
     [
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
     ]
     """, data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8],
                data[9], data[10], data[11], data[12], data[13], data[14], data[15]);
    }

    public static final Matrix4f ID = new Matrix4f(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);
}
