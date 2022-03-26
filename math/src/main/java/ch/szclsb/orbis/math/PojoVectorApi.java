package ch.szclsb.orbis.math;

public class PojoVectorApi<T extends FVector> implements IFVectorApi<T> {
    private final int size;

    public PojoVectorApi(Class<T> tClass) {
        try {
            var vec = tClass.getConstructor().newInstance();
            this.size = vec.getSize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(final T a, T b, T r) {
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
    public void mul(T a, T b, T r) {
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
    public float dot(T a, T b) {
        var result = 0;
        for (var i = 0; i < size; i++) {
            result += a.data[i] * b.data[i];
        }
        return result;
    }

    @Override
    public void cross(T a, T b, T r) {

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
