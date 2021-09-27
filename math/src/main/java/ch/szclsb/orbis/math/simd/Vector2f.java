package ch.szclsb.orbis.math.simd;

import ch.szclsb.orbis.math.IVector2f;
import ch.szclsb.orbis.math.MathUtils;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Vector2f implements IVector2f<Vector2f> {
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_64;

    public static final Vector2f ZERO = new Vector2f(0f, 0f);
    public static final Vector2f X = new Vector2f(1f, 0f);
    public static final Vector2f Y = new Vector2f(0f, 1f);
    public static final Vector2f XY = new Vector2f(1f, 1f);

    private final FloatVector data;

    Vector2f(FloatVector data) {
        this.data = data;
    }

    public Vector2f(float x, float y) {
        this.data = FloatVector.fromArray(SPECIES, new float[]{x, y}, 0);
    }

    @Override
    public Vector2f add(Vector2f vector) {
        return new Vector2f(data.add(vector.data));
    }

    @Override
    public Vector2f sub(Vector2f vector) {
        return new Vector2f(data.sub(vector.data));
    }

    @Override
    public Vector2f times(Vector2f vector) {
        return new Vector2f(data.mul(vector.data));
    }

    @Override
    public Vector2f times(float scale) {
        return new Vector2f(data.mul(scale));
    }

    @Override
    public float dot(Vector2f vector) {
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
    public float[] toArray() {
        return data.toArray();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2f vector) {
            return data.sub(vector.data).abs().lt(MathUtils.TOLERANCE).allTrue();
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f)", data.lane(0), data.lane(1));
    }
}
