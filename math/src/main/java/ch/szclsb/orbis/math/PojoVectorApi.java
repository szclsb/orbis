package ch.szclsb.orbis.math;

public class PojoVectorApi implements VectorAPI {
    private final int size;

    public PojoVectorApi(int size) {
        this.size = size;
    }

    @Override
    public void add(float[] a, float[] b, float[] r) {
        for (var i = 0; i < size; i++) {
            r[i] = a[i] + b[i];
        }
    }

    @Override
    public void add(float[] a, float s, float[] r) {
        for (var i = 0; i < size; i++) {
            r[i] = a[i] + s;
        }
    }

    @Override
    public void sub(float[] a, float[] b, float[] r) {
        for (var i = 0; i < size; i++) {
            r[i] = a[i] - b[i];
        }
    }

    @Override
    public void sub(float[] a, float s, float[] r) {
        for (var i = 0; i < size; i++) {
            r[i] = a[i] - s;
        }
    }

    @Override
    public void mul(float[] a, float[] b, float[] r) {
        for (var i = 0; i < size; i++) {
            r[i] = a[i] * b[i];
        }
    }

    @Override
    public void mul(float[] a, float s, float[] r) {
        for (var i = 0; i < size; i++) {
            r[i] = a[i] * s;
        }
    }

    @Override
    public void mmul(float[] a, float[] b, float[] r) {
        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size; j++) {
                var sum = 0;
                for (var k = 0; k < size; k++) {
                    sum += a[i * size + k] * b[k * size + j];
                }
                r[i * size + j] = sum;
            }
        }
    }

    @Override
    public float dot(float[] a, float[] b) {
        var result = 0;
        for (var i = 0; i < size; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    @Override
    public boolean equals(float[] a, float[] b) {
        var result = true;
        for (var i = 0; result && i < size; i++) {
            result = MathUtils.isFloatEquals(a[i], b[i]);
        }
        return result;
    }
}
