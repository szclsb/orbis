package ch.szclsb.orbis.math;

public interface IVector<V extends IVector<V>> {
    V add(V vector);
    V sub(V vector);
    V times(V vector);
    V times(float scalar);
    float dot(V vector);
    float normSquared();
    float norm();
    float[] toArray();
}
