package ch.szclsb.orbis.math;

public abstract class FMatrix {
    protected float[] data;

    public FMatrix(int size) {
        this.data = new float[size];
    }

    public FMatrix(float[] data) {
        this.data = data;
    }

    // used for pojo api
    public abstract int getRows();
    public abstract int getColumns();
}
