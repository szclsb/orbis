package ch.szclsb.orbis.math.simd;

import ch.szclsb.orbis.math.IVector3f;
import ch.szclsb.orbis.math.IVector4f;
import ch.szclsb.orbis.math.MathUtils;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Vector4f implements IVector4f<Vector4f> {
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;

    private final FloatVector data;

    Vector4f(FloatVector data) {
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
    @SuppressWarnings("unchecked")
    public Vector3f persDiv() {
        return new Vector3f(data.mul(1f / data.lane(3), Vector3f.MASK).mul(0f, Vector3f.MASK.not()));
    }

    @Override
    public float[] toArray() {
        return data.toArray();
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f, %.3f)", data.lane(0), data.lane(1), data.lane(2), data.lane(3));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector4f vector) {
            return data.sub(vector.data).abs().lt(MathUtils.TOLERANCE).allTrue();
        }
        return false;
    }
}
