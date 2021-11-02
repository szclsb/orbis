package ch.szclsb.orbis.math;

import ch.szclsb.orbis.processor.SimdMatrix;

@SimdMatrix(rows = 4, columns = 4)
public class FMatrix4x4 extends FMatrix {
    public FMatrix4x4(float a11, float a12, float a13, float a14,
                      float a21, float a22, float a23, float a24,
                      float a31, float a32, float a33, float a34,
                      float a41, float a42, float a43, float a44) {
        this.data = new float[] {
                a11, a12, a13, a14,
                a21, a22, a23, a24,
                a31, a32, a33, a34,
                a41, a42, a43, a44
        };
    }
}
