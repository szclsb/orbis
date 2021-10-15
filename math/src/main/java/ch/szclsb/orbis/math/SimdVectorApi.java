package ch.szclsb.orbis.math;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class SimdVectorApi implements VectorAPI {
    private final VectorSpecies<Float> species;
    private final int lanes, th, size;

    public SimdVectorApi(int size) {
        this(size, FloatVector.SPECIES_PREFERRED);
    }

    public SimdVectorApi(int size, VectorSpecies<Float> species) {
        this.species = species;
        this.lanes = species.length();
        this.th = (size / lanes) * lanes;
        this.size = size;
    }

    @Override
    public void add(float[] a, float[] b, float[] r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a, i);
            var vb = FloatVector.fromArray(species, b, i);
            va.add(vb).intoArray(r, i);
        }
        for (; i < size; i += 1) {
            r[i] = a[i] + b[i];
        }
    }

    @Override
    public void add(float[] a, float s, float[] r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a, i);
            va.add(s).intoArray(r, i);
        }
        for (; i < size; i += 1) {
            r[i] = a[i] + s;
        }
    }

    @Override
    public void sub(float[] a, float[] b, float[] r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a, i);
            var vb = FloatVector.fromArray(species, b, i);
            va.sub(vb).intoArray(r, i);
        }
        for (; i < size; i += 1) {
            r[i] = a[i] - b[i];
        }
    }

    @Override
    public void sub(float[] a, float s, float[] r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a, i);
            va.sub(s).intoArray(r, i);
        }
        for (; i < size; i += 1) {
            r[i] = a[i] - s;
        }
    }

    @Override
    public void mul(float[] a, float[] b, float[] r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a, i);
            var vb = FloatVector.fromArray(species, b, i);
            va.mul(vb).intoArray(r, i);
        }
        for (; i < size; i += 1) {
            r[i] = a[i] * b[i];
        }
    }

    @Override
    public void mul(float[] a, float s, float[] r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a, i);
            va.mul(s).intoArray(r, i);
        }
        for (; i < size; i += 1) {
            r[i] = a[i] * s;
        }
    }

    @Override
    public void mmul(float[] a, float[] b, float[] r) {

    }

    @Override
    public float dot(float[] a, float[] b) {
        var result = 0f;
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a, i);
            var vb = FloatVector.fromArray(species, b, i);
            result += va.mul(vb).reduceLanes(VectorOperators.ADD);
        }
        for (; i < size; i += 1) {
            result += a[i] * b[i];
        }
        return result;
    }

    @Override
    public boolean equals(float[] a, float[] b) {
        var result = true;
        var i = 0;
        for (; result && i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a, i);
            var vb = FloatVector.fromArray(species, b, i);
            result = va.sub(vb).abs().lt(MathUtils.TOLERANCE).allTrue();
        }
        for (; result && i < size; i += 1) {
            result = MathUtils.isFloatEquals(a[i], b[i]);
        }
        return result;
    }
}
