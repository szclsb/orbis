package ch.szclsb.orbis.math.simd;

import ch.szclsb.orbis.math.IVector4f;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Vector4f implements IVector4f<Vector4f> {
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;

    private final FloatVector data;

    private Vector4f(FloatVector data) {
        this.data = data;
    }

    public Vector4f(float x, float y, float z, float w) {
        data = FloatVector.fromArray(SPECIES, new float[] {x, y, z, w}, 0);
    }

    @Override
    public Vector4f add(Vector4f vector) {
        return new Vector4f(data.add(vector.data));
    }

    @Override
    public Vector4f sub(Vector4f vector) {
        return new Vector4f(data.sub(vector.data));
    }

    @Override
    public Vector4f mul(Vector4f vector) {
        return new Vector4f(data.mul(vector.data));
    }

    @Override
    public Vector4f mul(float scalar) {
        return new Vector4f(data.mul(scalar));
    }

    @Override
    public Vector4f cross(Vector4f vector) {
        return null;
    }

    @Override
    public float dot(Vector4f vector) {
        return data.mul(vector.data).reduceLanes(VectorOperators.ADD);
    }

    @Override
    public float normSquared() {
        return data.mul(data).reduceLanes(VectorOperators.ADD);
    }

    @Override
    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    @Override
    public Vector4f persDiv() {
        return mul(1f / data.lane(3));
    }

    @Override
    public float[] toArray() {
        return data.toArray();
    }
}
