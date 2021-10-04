package ch.szclsb.orbis.math;

public interface IVector3f<V extends IVector3f<V>> extends IVector<V> {
    V cross(V vector);
}
