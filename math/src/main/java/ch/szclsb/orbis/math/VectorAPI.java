package ch.szclsb.orbis.math;

public interface VectorAPI {
    void add(float[] a, float[] b, float [] r);
    void add(float[] a, float s, float [] r);
    void sub(float[] a, float[] b, float [] r);
    void sub(float[] a, float s, float [] r);
    void mul(float[] a, float[] b, float [] r);
    void mul(float[] a, float s, float [] r);
    void mmul(float[] a, float[] b, float [] r);
    float dot(float[] a, float[] b);
    boolean equals(float[] a, float[] b);
}
