package ch.szclsb.orbis.math;

import ch.szclsb.orbis.processor.SimdMatrix;

public class PojoMatrixApi<T extends FMatrix> implements IFMatrixApi<T> {
    private final int size;

    public PojoMatrixApi(Class<T> tClass) {
        var annotation = tClass.getAnnotation(SimdMatrix.class);
        this.size = annotation.rows() * annotation.columns();
    }

    @Override
    public void add(T a, T b, T r) {
        for (var i = 0; i < size; i++) {
            r.data[i] = a.data[i] + b.data[i];
        }
    }

    @Override
    public void add(T a, float s, T r) {
        for (var i = 0; i < size; i++) {
            r.data[i] = a.data[i] + s;
        }
    }

    @Override
    public void sub(T a, T b, T r) {
        for (var i = 0; i < size; i++) {
            r.data[i] = a.data[i] - b.data[i];
        }
    }

    @Override
    public void sub(T a, float s, T r) {
        for (var i = 0; i < size; i++) {
            r.data[i] = a.data[i] - s;
        }
    }

    @Override
    public void mul(T a, FMatrix b, FMatrix r) {
        //
    }

    @Override
    public void _mul(T a, T b, T r) {
        for (var i = 0; i < size; i++) {
            r.data[i] = a.data[i] * b.data[i];
        }
    }

    @Override
    public void mul(T a, float s, T r) {
        for (var i = 0; i < size; i++) {
            r.data[i] = a.data[i] * s;
        }
    }

    @Override
    public boolean equals(T a, T b) {
        var result = true;
        for (var i = 0; result && i < size; i++) {
            result = MathUtils.isFloatEquals(a.data[i], b.data[i]);
        }
        return result;
    }
}
