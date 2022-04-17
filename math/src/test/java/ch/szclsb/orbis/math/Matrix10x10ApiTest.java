package ch.szclsb.orbis.math;


import jdk.incubator.vector.FloatVector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UnitTest")
public class Matrix10x10ApiTest {
    @Nested
    public class PojoMatrix10x10ApiTest {
        private final PojoMatrixApi<FMatrix10x10> api = new PojoMatrixApi<>(FMatrix10x10.class);

        @Test
        public void testPojoMulMatrix() {
            testMulMatrix(api);
        }
    }

    @Nested
    public class ExSimdMatrix10x10ApiTest {
        private final ExternalSimdMatrixApi<FMatrix10x10> api = new ExternalSimdMatrixApi<>(FMatrix10x10.class, FloatVector.SPECIES_128);

        @Test
        public void testExSimdMulMatrix() {
            testMulMatrix(api);
        }
    }

    @Nested
    public class GenSimdMatrix10x10ApiTest {
        // Allow calling overloaded methods
        private final FMatrix10x10API api = new FMatrix10x10API();

        @Test
        /*
         * Allow calling overloaded faster method
         */
        public void testGenSimdMulMatrix() {
            var A = new FMatrix10x10(
                    6f, -5f, -6f, -5f, -5f, -6f, -2f, 10f, -5f, 1f,
                    -3f, 6f, -8f, 1f, 3f, 6f, 5f, -8f, -3f, 6f,
                    10f, -7f, 1f, -9f, -7f, 3f, -7f, -1f, 3f, 5f,
                    -9f, -2f, 6f, 6f, -9f, 2f, 4f, -1f, -4f, -8f,
                    6f, -9f, 8f, 5f, -10f, 6f, 1f, 6f, 0f, -7f,
                    6f, 3f, 4f, 7f, -10f, -6f, 3f, 1f, 2f, 8f,
                    4f, 5f, -10f, 3f, 2f, -5f, 2f, 3f, 10f, -5f,
                    8f, 6f, 5f, -4f, -2f, 2f, -1f, 9f, 3f, -6f,
                    8f, -3f, -3f, -3f, 5f, -9f, 3f, 7f, -4f, 1f,
                    -2f, -1f, -6f, -7f, 9f, 8f, 1f, 0f, -2f, 7f
            );
            var B = new FMatrix10x10(
                    7f, 2f, 2f, -6f, 3f, -8f, -10f, -4f, -3f, 8f,
                    7f, -2f, 3f, -2f, 9f, 10f, 0f, -6f, 9f, -6f,
                    5f, 7f, 6f, -6f, 10f, -7f, 2f, -1f, 8f, -4f,
                    7f, 3f, -9f, -5f, -10f, -10f, -3f, 4f, 0f, -7f,
                    -2f, -9f, -1f, -6f, 6f, 3f, 7f, -4f, 9f, 8f,
                    4f, -1f, 10f, 0f, -4f, 2f, 9f, -5f, 9f, -9f,
                    -7f, -4f, 2f, -9f, 5f, 1f, 4f, -9f, 10f, 10f,
                    0f, 0f, -5f, -8f, -2f, 3f, 6f, -9f, -6f, 10f,
                    4f, 8f, 5f, -1f, -5f, -4f, 6f, -6f, 7f, -8f,
                    9f, -3f, -5f, 8f, 0f, 4f, -7f, 1f, -8f, -2f
            );
            var R = new FMatrix10x10();
            api.mul(A, B, R);
            assertArrayEquals(new float[]{
                    -69f, -19f, -133f, 16f, -48f, 19f, -131f, 1f, -333f, 269f,
                    13f, -166f, 17f, 101f, 5f, 168f, -2f, -3f, 109f, -83f,
                    95f, 111f, 104f, 143f, -35f, -84f, -144f, 37f, -204f, -16f,
                    -95f, 101f, 20f, -42f, -65f, -88f, 81f, 81f, 76f, -138f,
                    28f, 202f, 65f, -144f, -124f, -271f, 14f, -18f, -41f, -15f,
                    187f, 131f, -97f, -14f, -18f, -128f, -223f, 12f, -129f, -53f,
                    -9f, 11f, -52f, -93f, -87f, 5f, 21f, -117f, 38f, 62f,
                    72f, 89f, 120f, -172f, 110f, -11f, 56f, -187f, 75f, 70f,
                    -75f, -91f, -133f, -110f, 84f, -2f, -100f, -63f, -159f, 366f,
                    -38f, -195f, 48f, 80f, 32f, 198f, 107f, -74f, 42f, 75
            }, R.data, MathUtils.TOLERANCE);
        }
    }

    protected void testMulMatrix(IFMatrixApi<FMatrix10x10> api) {
        var A = new FMatrix10x10(
                6f, -5f, -6f, -5f, -5f, -6f, -2f, 10f, -5f, 1f,
                -3f, 6f, -8f, 1f, 3f, 6f, 5f, -8f, -3f, 6f,
                10f, -7f, 1f, -9f, -7f, 3f, -7f, -1f, 3f, 5f,
                -9f, -2f, 6f, 6f, -9f, 2f, 4f, -1f, -4f, -8f,
                6f, -9f, 8f, 5f, -10f, 6f, 1f, 6f, 0f, -7f,
                6f, 3f, 4f, 7f, -10f, -6f, 3f, 1f, 2f, 8f,
                4f, 5f, -10f, 3f, 2f, -5f, 2f, 3f, 10f, -5f,
                8f, 6f, 5f, -4f, -2f, 2f, -1f, 9f, 3f, -6f,
                8f, -3f, -3f, -3f, 5f, -9f, 3f, 7f, -4f, 1f,
                -2f, -1f, -6f, -7f, 9f, 8f, 1f, 0f, -2f, 7f
        );
        var B = new FMatrix10x10(
                7f, 2f, 2f, -6f, 3f, -8f, -10f, -4f, -3f, 8f,
                7f, -2f, 3f, -2f, 9f, 10f, 0f, -6f, 9f, -6f,
                5f, 7f, 6f, -6f, 10f, -7f, 2f, -1f, 8f, -4f,
                7f, 3f, -9f, -5f, -10f, -10f, -3f, 4f, 0f, -7f,
                -2f, -9f, -1f, -6f, 6f, 3f, 7f, -4f, 9f, 8f,
                4f, -1f, 10f, 0f, -4f, 2f, 9f, -5f, 9f, -9f,
                -7f, -4f, 2f, -9f, 5f, 1f, 4f, -9f, 10f, 10f,
                0f, 0f, -5f, -8f, -2f, 3f, 6f, -9f, -6f, 10f,
                4f, 8f, 5f, -1f, -5f, -4f, 6f, -6f, 7f, -8f,
                9f, -3f, -5f, 8f, 0f, 4f, -7f, 1f, -8f, -2f
        );
        var R = new FMatrix10x10();
        api.mul(A, B, R);
        assertArrayEquals(new float[]{
                -69f, -19f, -133f, 16f, -48f, 19f, -131f, 1f, -333f, 269f,
                13f, -166f, 17f, 101f, 5f, 168f, -2f, -3f, 109f, -83f,
                95f, 111f, 104f, 143f, -35f, -84f, -144f, 37f, -204f, -16f,
                -95f, 101f, 20f, -42f, -65f, -88f, 81f, 81f, 76f, -138f,
                28f, 202f, 65f, -144f, -124f, -271f, 14f, -18f, -41f, -15f,
                187f, 131f, -97f, -14f, -18f, -128f, -223f, 12f, -129f, -53f,
                -9f, 11f, -52f, -93f, -87f, 5f, 21f, -117f, 38f, 62f,
                72f, 89f, 120f, -172f, 110f, -11f, 56f, -187f, 75f, 70f,
                -75f, -91f, -133f, -110f, 84f, -2f, -100f, -63f, -159f, 366f,
                -38f, -195f, 48f, 80f, 32f, 198f, 107f, -74f, 42f, 75
        }, R.data, MathUtils.TOLERANCE);
    }
}
