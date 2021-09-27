package ch.szclsb.orbis.math;

public interface IVector2f<V extends IVector2f<V>> {
    V add(V vector);
    V sub(V vector);
    V times(V vector);
    V times(float scalar);
    float dot(V vector);
    float normSquared();
    float norm();
    float[] toArray();
}
