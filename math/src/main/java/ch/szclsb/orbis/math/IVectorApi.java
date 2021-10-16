package ch.szclsb.orbis.math;

public interface IVectorApi<T extends Vector> {
    void add(T a, T b, T r);
    void add(T a, float s, T r);
    void sub(T a, T b, T r);
    void sub(T a, float s, T r);
    void mul(T a, T b, T r);
    void mul(T a, float s, T r);
//    void mmul(float[] a, float[] b, float [] r);
    float dot(T a, T b);
    boolean equals(T a, T b);
}
