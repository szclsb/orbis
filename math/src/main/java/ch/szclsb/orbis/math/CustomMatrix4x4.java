package ch.szclsb.orbis.math;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public class CustomMatrix4x4 {
    private static final VectorSpecies<Float> species = FloatVector.SPECIES_128;

    private final FloatVector row1;
    private final FloatVector row2;
    private final FloatVector row3;
    private final FloatVector row4;

    public CustomMatrix4x4() {
        row1 = FloatVector.zero(species);
        row2 = FloatVector.zero(species);
        row3 = FloatVector.zero(species);
        row4 = FloatVector.zero(species);
    }

    public CustomMatrix4x4(FloatVector row1, FloatVector row2, FloatVector row3, FloatVector row4) {
        this.row1 = row1;
        this.row2 = row2;
        this.row3 = row3;
        this.row4 = row4;
    }

    public CustomMatrix4x4(float a11, float a12, float a13, float a14,
                           float a21, float a22, float a23, float a24,
                           float a31, float a32, float a33, float a34,
                           float a41, float a42, float a43, float a44) {
        row1 = FloatVector.fromArray(species, new float[]{a11, a12, a13, a14}, 0);
        row2 = FloatVector.fromArray(species, new float[]{a21, a22, a23, a24}, 0);
        row3 = FloatVector.fromArray(species, new float[]{a31, a32, a33, a34}, 0);
        row4 = FloatVector.fromArray(species, new float[]{a41, a42, a43, a44}, 0);
    }

    public CustomMatrix4x4(float[] data) {
        row1 = FloatVector.fromArray(species, data, 0);
        row2 = FloatVector.fromArray(species, data, 4);
        row3 = FloatVector.fromArray(species, data, 8);
        row4 = FloatVector.fromArray(species, data, 12);
    }

    CustomMatrix4x4 add(CustomMatrix4x4 other) {
        return new CustomMatrix4x4(
                this.row1.add(other.row1),
                this.row2.add(other.row2),
                this.row3.add(other.row3),
                this.row4.add(other.row4)
        );
    }

    CustomMatrix4x4 add(float s) {
        return new CustomMatrix4x4(
                this.row1.add(s),
                this.row2.add(s),
                this.row3.add(s),
                this.row4.add(s)
        );
    }

    CustomMatrix4x4 sub(CustomMatrix4x4 other) {
        return new CustomMatrix4x4(
                this.row1.sub(other.row1),
                this.row2.sub(other.row2),
                this.row3.sub(other.row3),
                this.row4.sub(other.row4)
        );
    }

    CustomMatrix4x4 sub(float s) {
        return new CustomMatrix4x4(
                this.row1.sub(s),
                this.row2.sub(s),
                this.row3.sub(s),
                this.row4.sub(s)
        );
    }

    CustomMatrix4x4 mul(CustomMatrix4x4 other) {
        return new CustomMatrix4x4(
                other.row1.mul(this.row1.lane(0))
                        .add(other.row2.mul(this.row1.lane(1)))
                        .add(other.row3.mul(this.row1.lane(2)))
                        .add(other.row4.mul(this.row1.lane(3))),
                other.row1.mul(this.row2.lane(0))
                        .add(other.row2.mul(this.row2.lane(1)))
                        .add(other.row3.mul(this.row2.lane(2)))
                        .add(other.row4.mul(this.row2.lane(3))),
                other.row1.mul(this.row3.lane(0))
                        .add(other.row2.mul(this.row3.lane(1)))
                        .add(other.row3.mul(this.row3.lane(2)))
                        .add(other.row4.mul(this.row3.lane(3))),
                other.row1.mul(this.row4.lane(0))
                        .add(other.row2.mul(this.row4.lane(1)))
                        .add(other.row3.mul(this.row4.lane(2)))
                        .add(other.row4.mul(this.row4.lane(3)))
        );
    }

    CustomMatrix4x4 mul(float s) {
        return new CustomMatrix4x4(
                this.row1.mul(s),
                this.row2.mul(s),
                this.row3.mul(s),
                this.row4.mul(s)
        );
    }

    float[] toArray() {
        var result = new float[16];
        row1.intoArray(result, 0);
        row2.intoArray(result, 4);
        row3.intoArray(result, 8);
        row4.intoArray(result, 12);
        return result;
    }
}
