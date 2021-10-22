package ch.szclsb.orbis.math;

public interface IFVectorApi<T extends FVector> {
    void add(T a, T b, T r);
    void add(T a, float s, T r);
    void sub(T a, T b, T r);
    void sub(T a, float s, T r);
    void mul(T a, T b, T r);
    void mul(T a, float s, T r);
    float dot(T a, T b);
    void cross(T a, T b, T r);
    boolean equals(T a, T b);
}
