package ch.szclsb.orbis.math;

public interface IVector3f<V extends IVector3f<V>> {
    V add(V vector);
    V sub(V vector);
    V mul(V vector);
    V mul(float scalar);
    V cross(V vector);
    float dot(V vector);
    float normSquared();
    float norm();
    float[] toArray();
}