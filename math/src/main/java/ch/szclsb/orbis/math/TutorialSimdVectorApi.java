package ch.szclsb.orbis.math;

import ch.szclsb.orbis.processor.SimdVec;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class TutorialSimdVectorApi<T extends FVector> implements IFVectorApi<T> {
    private final VectorSpecies<Float> species;
    private final int lanes, th, size;

    public TutorialSimdVectorApi(Class<T> tClass) {
        this(tClass, FloatVector.SPECIES_PREFERRED);
    }

    public TutorialSimdVectorApi(Class<T> tClass, VectorSpecies<Float> species) {
        this.species = species;
        this.lanes = species.length();
        this.size = tClass.getAnnotation(SimdVec.class).lanes();
        this.th = (size / lanes) * lanes;
    }

    @Override
    public void add(T a, T b, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            va.add(vb).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] + b.data[i];
        }
    }

    @Override
    public void add(T a, float s, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            va.add(s).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] + s;
        }
    }

    @Override
    public void sub(T a, T b, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            va.sub(vb).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] - b.data[i];
        }
    }

    @Override
    public void sub(T a, float s, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            va.sub(s).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] - s;
        }
    }

    @Override
    public void mul(T a, T b, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            va.mul(vb).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] * b.data[i];
        }
    }

    @Override
    public void mul(T a, float s, T r) {
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            va.mul(s).intoArray(r.data, i);
        }
        for (; i < size; i += 1) {
            r.data[i] = a.data[i] * s;
        }
    }

    @Override
    public float dot(T a, T b) {
        var result = 0f;
        var i = 0;
        for (; i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            result += va.mul(vb).reduceLanes(VectorOperators.ADD);
        }
        for (; i < size; i += 1) {
            result += a.data[i] * b.data[i];
        }
        return result;
    }

    @Override
    public void cross(T a, T b, T r) {

    }

    @Override
    public boolean equals(T a, T b) {
        var result = true;
        var i = 0;
        for (; result && i < th; i += lanes) {
            var va = FloatVector.fromArray(species, a.data, i);
            var vb = FloatVector.fromArray(species, b.data, i);
            result = va.sub(vb).abs().lt(MathUtils.TOLERANCE).allTrue();
        }
        for (; result && i < size; i += 1) {
            result = MathUtils.isFloatEquals(a.data[i], b.data[i]);
        }
        return result;
    }
}
