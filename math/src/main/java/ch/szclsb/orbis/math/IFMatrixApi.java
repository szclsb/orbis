package ch.szclsb.orbis.math;

public interface IFMatrixApi<T extends FMatrix> {
    void add(T a, T b, T r);
    void add(T a, float s, T r);
    void sub(T a, T b, T r);
    void sub(T a, float s, T r);
    void mul(T a, FMatrix b, FMatrix r);
    void mul(T a, float s, T r);
    void _mul(T a, T b, T r);
    boolean equals(T a, T b);
}
