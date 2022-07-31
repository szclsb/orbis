package ch.szclsb.orbis.math;

public interface IFMatrixApi<A extends FMatrix> {
    void add(A a, A b, A r);
    void add(A a, float s, A r);
    void sub(A a, A b, A r);
    void sub(A a, float s, A r);
    void mul(A a, FMatrix b, FMatrix r);
    void mul(A a, float s, A r);
    void _mul(A a, A b, A r);  //element wise
    boolean equals(A a, A b);
}
