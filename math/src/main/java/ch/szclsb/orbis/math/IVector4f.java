package ch.szclsb.orbis.math;

public interface IVector4f<V extends IVector4f<V>> extends IVector<V> {
//    V cross(V vector);
    <T extends IVector3f<T>> T persDiv();
}
