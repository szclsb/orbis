package ch.szclsb.orbis.math.jni;

import ch.szclsb.orbis.math.IVector3f;
import ch.szclsb.orbis.math.IVector4f;
import ch.szclsb.orbis.math.MathUtils;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Vector4f implements IVector4f<Vector4f> {
    @Override
    public Vector4f add(Vector4f vector) {
        return null;
    }

    @Override
    public Vector4f sub(Vector4f vector) {
        return null;
    }

    @Override
    public Vector4f mul(Vector4f vector) {
        return null;
    }

    @Override
    public Vector4f mul(float scalar) {
        return null;
    }

    @Override
    public float dot(Vector4f vector) {
        return 0;
    }

    @Override
    public float normSquared() {
        return 0;
    }

    @Override
    public float norm() {
        return 0;
    }

    @Override
    public <T extends IVector3f<T>> T persDiv() {
        return null;
    }

    @Override
    public float[] toArray() {
        return new float[0];
    }
}
