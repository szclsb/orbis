package ch.szclsb.orbis.math;

import ch.szclsb.orbis.processor.SimdVec;

@SimdVec(lanes = 4)
public class FVector4 extends FVector {
    public FVector4() {
        super(4);
    }

    public FVector4(float x, float y, float z, float w) {
        super(new float[]{x, y, z, w});
    }
}
