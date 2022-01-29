package ch.szclsb.orbis.math;

import ch.szclsb.orbis.processor.SimdMatrix;

@SimdMatrix(rows = FMatrix10x10.ROWS, columns = FMatrix10x10.COLUMNS)
public class FMatrix10x10 extends FMatrix {
    public static final int ROWS = 10;
    public static final int COLUMNS = 10;

    public FMatrix10x10() {
        super(ROWS * COLUMNS);
    }

    public FMatrix10x10(float a1c1, float a1c2, float a1c3, float a1c4, float a1c5, float a1c6, float a1c7, float a1c8, float a1c9, float a1c10,
                        float a2c1, float a2c2, float a2c3, float a2c4, float a2c5, float a2c6, float a2c7, float a2c8, float a2c9, float a2c10,
                        float a3c1, float a3c2, float a3c3, float a3c4, float a3c5, float a3c6, float a3c7, float a3c8, float a3c9, float a3c10,
                        float a4c1, float a4c2, float a4c3, float a4c4, float a4c5, float a4c6, float a4c7, float a4c8, float a4c9, float a4c10,
                        float a5c1, float a5c2, float a5c3, float a5c4, float a5c5, float a5c6, float a5c7, float a5c8, float a5c9, float a5c10,
                        float a6c1, float a6c2, float a6c3, float a6c4, float a6c5, float a6c6, float a6c7, float a6c8, float a6c9, float a6c10,
                        float a7c1, float a7c2, float a7c3, float a7c4, float a7c5, float a7c6, float a7c7, float a7c8, float a7c9, float a7c10,
                        float a8c1, float a8c2, float a8c3, float a8c4, float a8c5, float a8c6, float a8c7, float a8c8, float a8c9, float a8c10,
                        float a9c1, float a9c2, float a9c3, float a9c4, float a9c5, float a9c6, float a9c7, float a9c8, float a9c9, float a9c10,
                        float a10c1, float a10c2, float a10c3, float a10c4, float a10c5, float a10c6, float a10c7, float a10c8, float a10c9, float a10c10) {
        super(new float[]{
                a1c1, a1c2, a1c3, a1c4, a1c5, a1c6, a1c7, a1c8, a1c9, a1c10,
                a2c1, a2c2, a2c3, a2c4, a2c5, a2c6, a2c7, a2c8, a2c9, a2c10,
                a3c1, a3c2, a3c3, a3c4, a3c5, a3c6, a3c7, a3c8, a3c9, a3c10,
                a4c1, a4c2, a4c3, a4c4, a4c5, a4c6, a4c7, a4c8, a4c9, a4c10,
                a5c1, a5c2, a5c3, a5c4, a5c5, a5c6, a5c7, a5c8, a5c9, a5c10,
                a6c1, a6c2, a6c3, a6c4, a6c5, a6c6, a6c7, a6c8, a6c9, a6c10,
                a7c1, a7c2, a7c3, a7c4, a7c5, a7c6, a7c7, a7c8, a7c9, a7c10,
                a8c1, a8c2, a8c3, a8c4, a8c5, a8c6, a8c7, a8c8, a8c9, a8c10,
                a9c1, a9c2, a9c3, a9c4, a9c5, a9c6, a9c7, a9c8, a9c9, a9c10,
                a10c1, a10c2, a10c3, a10c4, a10c5, a10c6, a10c7, a10c8, a10c9, a10c10
        });
    }

    // used for pojo api
    @Override
    int getRows() {
        return ROWS;
    }

    @Override
    int getColumns() {
        return COLUMNS;
    }
}
