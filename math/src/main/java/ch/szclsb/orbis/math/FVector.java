package ch.szclsb.orbis.math;

public abstract class FVector {
    protected final float[] data;

    public FVector(int size) {
        this.data = new float[size];
    }

    public FVector(float[] data) {
        this.data = data;
    }

    public int getSize() {
        return data.length;
    }
}
