package ch.szclsb.orbis.math;

import ch.szclsb.orbis.math.simd.Vector4f;

public interface IVector4f<V extends IVector4f<V>> {
    V add(V vector);
    V sub(V vector);
    V mul(V vector);
    V mul(float scalar);
//    V cross(V vector);
    float dot(V vector);
    float normSquared();
    float norm();
    <T extends IVector3f<T>> T persDiv();
    float[] toArray();
}
