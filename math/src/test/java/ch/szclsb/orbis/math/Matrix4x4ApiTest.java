package ch.szclsb.orbis.math;


import jdk.incubator.vector.FloatVector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UnitTest")
public class Matrix4x4ApiTest {
    @Nested
    public class PojoMatrix4x4ApiTest {
        private final PojoMatrixApi<FMatrix4x4> api = new PojoMatrixApi<>(FMatrix4x4.class);

        @Test
        public void testPojoEqual() {
            testEqual(api);
            testNotEquals(api);
        }

        @Test
        public void testPojoAdd() {
            testAdd(api);
        }

        @Test
        public void testPojoAddScalar() {
            testAddScalar(api);
        }

        @Test
        public void testPojoSub() {
            testSub(api);
        }

        @Test
        public void testPojoSubScalar() {
            testSubScalar(api);
        }

        @Test
        public void testPojoMulElements() {
            testMulElements(api);
        }

        @Test
        public void testPojoMulScalar() {
            testMulScalar(api);
        }

        @Test
        public void testPojoMulMatrix() {
            testMulMatrix(api);
        }
    }

    @Nested
    public class ExSimdMatrix4x4ApiTest {
        private final ExternalSimdMatrixApi<FMatrix4x4> api = new ExternalSimdMatrixApi<>(FMatrix4x4.class, FloatVector.SPECIES_128);

        @Test
        public void testExSimdEqual() {
            testEqual(api);
            testNotEquals(api);
        }

        @Test
        public void testExSimdAdd() {
            testAdd(api);
        }

        @Test
        public void testExSimdAddScalar() {
            testAddScalar(api);
        }

        @Test
        public void testExSimdSub() {
            testSub(api);
        }

        @Test
        public void testExSimdSubScalar() {
            testSubScalar(api);
        }

        @Test
        public void testExSimdMulElements() {
            testMulElements(api);
        }

        @Test
        public void testExSimdMulScalar() {
            testMulScalar(api);
        }

        @Test
        public void testExSimdMulMatrix() {
            testMulMatrix(api);
        }
    }

    @Nested
    public class GenSimdMatrix4x4ApiTest {
        // Allow calling overloaded methods
        private final FMatrix4x4API api = new FMatrix4x4API();

        @Test
        public void testGenSimdEqual() {
            testEqual(api);
            testNotEquals(api);
        }

        @Test
        public void testGenSimdAdd() {
            testAdd(api);
        }

        @Test
        public void testGenSimdAddScalar() {
            testAddScalar(api);
        }

        @Test
        public void testGenSimdSub() {
            testSub(api);
        }

        @Test
        public void testGenSimdSubScalar() {
            testSubScalar(api);
        }

        @Test
        public void testGenSimdMulElements() {
            testMulElements(api);
        }

        @Test
        public void testGenSimdMulScalar() {
            testMulScalar(api);
        }

        @Test
        @Disabled
        /*
         * Allow calling overloaded faster method
         */
        public void testGenSimdMulMatrix() {
            var A = new FMatrix4x4(
                    4f, 8f, 3f, 1f,
                    15f, 6f, 7f, 12f,
                    9f, 2f, 11f, 9f,
                    5f, 3f, 7f, 2f
            );
            var B = new FMatrix4x4(
                    7f, 10f, 3f, 14f,
                    11f, 6f, 7f, 13f,
                    1, 4f, 15f, 5f,
                    12f, 8f, 2f, 9f
            );
            var R = new FMatrix4x4();
            api.mul(A, B, R);
            assertArrayEquals(new float[]{
                    131f, 108f, 115f, 184f,
                    322f, 310f, 216f, 431f,
                    204f, 218f, 224f, 288f,
                    99f, 112f, 145f, 162f
            }, R.data);
        }
    }

    protected void testEqual(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f
        );
        var B = new FMatrix4x4(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f
        );
        assertTrue(api.equals(A, B));
    }

    protected void testNotEquals(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f
        );
        var B = new FMatrix4x4(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 0f, 12f,
                13f, 14f, 15f, 16f
        );
        assertFalse(api.equals(A, B));
    }

    protected void testAdd(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );
        var B = new FMatrix4x4(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );
        var R = new FMatrix4x4();
        api.add(A, B, R);
        assertArrayEquals(new float[]{
                11f, 18f, 6f, 15f,
                26f, 12f, 14f, 25f,
                10f, 6f, 26f, 14f,
                17f, 11f, 9f, 11f
        }, R.data);
    }

    protected void testAddScalar(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );
        var R = new FMatrix4x4();
        api.add(A, 3f, R);
        assertArrayEquals(new float[]{
                7f, 11f, 6f, 4f,
                18f, 9f, 10f, 15f,
                12f, 5f, 14f, 12f,
                8f, 6f, 10f, 5f
        }, R.data);
    }

    protected void testSub(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );
        var B = new FMatrix4x4(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );
        var R = new FMatrix4x4();
        api.sub(A, B, R);
        assertArrayEquals(new float[]{
                -3f, -2f, 0f, -13f,
                4f, 0f, 0f, -1f,
                8f, -2f, -4f, 4f,
                -7f, -5f, 5f, -7f
        }, R.data);
    }

    protected void testSubScalar(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );
        var R = new FMatrix4x4();
        api.sub(A, 3f, R);
        assertArrayEquals(new float[]{
                1f, 5f, 0f, -2f,
                12f, 3f, 4f, 9f,
                6f, -1f, 8f, 6f,
                2f, 0f, 4f, -1f
        }, R.data);
    }

    protected void testMulElements(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );
        var B = new FMatrix4x4(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );
        var R = new FMatrix4x4();
        api._mul(A, B, R);
        assertArrayEquals(new float[]{
                28f, 80f, 9f, 14f,
                165f, 36f, 49f, 156f,
                9f, 8f, 165f, 45f,
                60f, 24f, 14f, 18f
        }, R.data);
    }

    protected void testMulScalar(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );
        var R = new FMatrix4x4();
        api.mul(A, 3f, R);
        assertArrayEquals(new float[]{
                12f, 24, 9f, 3f,
                45f, 18f, 21f, 36f,
                27f, 6f, 33f, 27f,
                15f, 9f, 21f, 6f
        }, R.data);
    }

    protected void testMulMatrix(IFMatrixApi<FMatrix4x4> api) {
        var A = new FMatrix4x4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );
        var B = new FMatrix4x4(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );
        var R = new FMatrix4x4();
        api.mul(A, B, R);
        assertArrayEquals(new float[]{
                131f, 108f, 115f, 184f,
                322f, 310f, 216f, 431f,
                204f, 218f, 224f, 288f,
                99f, 112f, 145f, 162f
        }, R.data);
    }
}
