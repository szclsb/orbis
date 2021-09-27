package ch.szclsb.orbis.math.simd;

import ch.szclsb.orbis.math.IMat4f;
import ch.szclsb.orbis.math.MathUtils;
import jdk.incubator.vector.*;

public class Matrix4f implements IMat4f<Matrix4f> {
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_512;
    private static final VectorSpecies<Float> SPECIES_DIM = FloatVector.SPECIES_128;

    private static final VectorShuffle<Float> TRANSPOSE = VectorShuffle.fromValues(SPECIES,
            0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15);


    private static final int DIM = 4;
    private static final int SIZE = DIM * DIM;

    private final FloatVector data;

    public Matrix4f(float a11, float a12, float a13, float a14,
                    float a21, float a22, float a23, float a24,
                    float a31, float a32, float a33, float a34,
                    float a41, float a42, float a43, float a44) {
        this.data = FloatVector.fromArray(SPECIES, new float[]{
                a11, a12, a13, a14,
                a21, a22, a23, a24,
                a31, a32, a33, a34,
                a41, a42, a43, a44
        }, 0);
    }

    public Matrix4f() {
        this.data = FloatVector.fromArray(SPECIES, new float[16], 0);
    }

    Matrix4f(FloatVector data) {
        this.data = data;
    }

    @Override
    public Matrix4f add(Matrix4f mat) {
        return new Matrix4f(data.add(mat.data));
    }

    @Override
    public Matrix4f sub(Matrix4f mat) {
        return new Matrix4f(data.sub(mat.data));
    }

    @Override
    public Matrix4f times(Matrix4f mat) {
        return times(mat, false);
    }

    public Matrix4f transpose() {
        return new Matrix4f(data.rearrange(TRANSPOSE));
    }

    @Override
    public Matrix4f times(Matrix4f mat, boolean elementwise) {
        if (!elementwise) {
            var resultArray = new float[SIZE];
            var tMat = mat.transpose();
            // cache columns of matrix to add
            var cols = new FloatVector[4];
            for (var i = 0; i < DIM; i++) {
                cols[i] = (FloatVector) tMat.data.castShape(SPECIES_DIM, i);
            }
            // calc matrix multiplication
            for (var i = 0; i < DIM; i++) {
                var row = (FloatVector) data.castShape(SPECIES_DIM, i);
                for (var j = 0; j < DIM; j++) {
                     resultArray[i * DIM + j] = row.mul(cols[j]).reduceLanes(VectorOperators.ADD);
                }
            }
            return new Matrix4f(FloatVector.fromArray(SPECIES, resultArray, 0));
        } else {
            return new Matrix4f(data.mul(mat.data));
        }
    }

    @Override
    public Matrix4f times(float scalar) {
        return new Matrix4f(data.mul(scalar));
    }

    @Override
    public float[] toArray() {
        return data.toArray();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Matrix4f mat) {
            return data.sub(mat.data).abs().lt(MathUtils.TOLERANCE).allTrue();
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("""
     [
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
     ]
     """,       data.lane(0), data.lane(1), data.lane(2), data.lane(3),
                data.lane(4), data.lane(5), data.lane(6), data.lane(7),
                data.lane(8), data.lane(9), data.lane(10), data.lane(11),
                data.lane(12), data.lane(13), data.lane(14), data.lane(15));
    }
}
