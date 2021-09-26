package ch.szclsb.orbis.math.simd;

import ch.szclsb.orbis.math.IVector3f;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Vector3f implements IVector3f<Vector3f> {
    // no 96 bit (3*32 bit) mask available, for better performance (full simd advantage) we use 128 bit (4*32 bit)
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;
    private static final VectorMask<Float> MASK = SPECIES.loadMask(new boolean[]{true, true, true, false}, 0);

    private final FloatVector data;

    private Vector3f(FloatVector data) {
        this.data = data;
    }

    public Vector3f(float x, float y, float z) {
        data = FloatVector.fromArray(SPECIES, new float[] {x, y, z, 0}, 0);
    }

    @Override
    public Vector3f add(Vector3f vector) {
        return new Vector3f(data.add(vector.data, MASK));
    }

    @Override
    public Vector3f sub(Vector3f vector) {
        return new Vector3f(data.sub(vector.data, MASK));
    }

    @Override
    public Vector3f mul(Vector3f vector) {
        return new Vector3f(data.mul(vector.data, MASK));
    }

    @Override
    public Vector3f mul(float scale) {
        return new Vector3f(data.mul(scale, MASK));
    }

    @Override
    public Vector3f cross(Vector3f vector) {
        return null;
    }

    @Override
    public float dot(Vector3f vector) {
        return data.mul(vector.data, MASK).reduceLanes(VectorOperators.ADD, MASK);
    }

    @Override
    public float normSquared() {
        return data.mul(data, MASK).reduceLanes(VectorOperators.ADD, MASK);
    }

    @Override
    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    @Override
    public float[] toArray() {
        var result = new float[3];
        data.intoArray(result, 0, MASK);
        return result;
    }
}
