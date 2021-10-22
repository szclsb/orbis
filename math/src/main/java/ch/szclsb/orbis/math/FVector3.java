package ch.szclsb.orbis.math;

import ch.szclsb.orbis.processor.SimdVec;

@SimdVec(lanes = 3)
public class FVector3 extends FVector {
    public FVector3() {
        super(3);
    }

    public FVector3(float x, float y, float z) {
        super(new float[]{x, y, z, 0});
    }
}
