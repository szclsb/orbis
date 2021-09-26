package ch.szclsb.orbis.math.simd;

import ch.szclsb.orbis.math.IVector2f;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Vector2f implements IVector2f<Vector2f> {
    public static final Vector2f ZERO = new Vector2f(0f, 0f);
    public static final Vector2f X = new Vector2f(1f, 0f);
    public static final Vector2f Y = new Vector2f(0f, 1f);
    public static final Vector2f XY = new Vector2f(1f, 1f);

    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_64;

    private final FloatVector data;

    private Vector2f(FloatVector data) {
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
    public Vector2f mul(Vector2f vector) {
        return new Vector2f(data.mul(vector.data));
    }

    @Override
    public Vector2f mul(float scale) {
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

}
