package ch.szclsb.orbis.math;

public class PojoMatrixApi<T extends FMatrix> implements IFMatrixApi<T> {
    private final int size;

    public PojoMatrixApi(Class<T> tClass) {
        try {
            var mat = tClass.getConstructor().newInstance();
            this.size = mat.getRows() * mat.getColumns();
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
    public <B extends FMatrix, R extends FMatrix> void mul(T a, B b, R r) {
        if (a.getColumns() == b.getRows() && a.getRows() == r.getRows() && b.getColumns() == r.getColumns()) {
            var rows = a.getRows();
            var cols = b.getColumns();
            var depth = a.getColumns();
            for (var i = 0; i < rows; i++) {
                for (var j = 0; j < cols; j++) {
                    var sum = 0;
                    for (var k = 0; k < depth; k++) {
                        sum += a.data[i * depth + k] * b.data[k * depth + j];
                    }
                    r.data[i * cols + j] = sum;
                }
            }
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
