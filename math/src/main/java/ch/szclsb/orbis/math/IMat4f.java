package ch.szclsb.orbis.math;

public interface IMat4f<M extends IMat4f<M>> {
        M add(M mat);
        M sub(M mat);
        M times(M mat);
        M times(M mat, boolean elementwise);
//        <V extends IVector4f<V>> M times(V vector);
        M times(float scalar);
        float[] toArray();
}
