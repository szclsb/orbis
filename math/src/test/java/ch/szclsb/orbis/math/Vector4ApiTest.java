package ch.szclsb.orbis.math;

import jdk.incubator.vector.FloatVector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UnitTest")
public class Vector4ApiTest {
    @Nested
    public class PojoVector4ApiTest {
        private final PojoVectorApi<FVector4> api = new PojoVectorApi<>(FVector4.class);

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
        public void testPojoMul() {
            testMul(api);
        }

        @Test
        public void testPojoMulScalar() {
            testMulScalar(api);
        }

        @Test
        public void testPojoDot() {
            testDot(api);
        }

        @Test
        @Disabled
        public void testPojoCross() {
            testCross(api);
        }
    }

    @Nested
    public class ExSimdVector4ApiTest {
        private final ExternalSimdVectorApi<FVector4> api = new ExternalSimdVectorApi<>(FVector4.class, FloatVector.SPECIES_128);

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
        public void testExSimdMul() {
            testMul(api);
        }

        @Test
        public void testExSimdMulScalar() {
            testMulScalar(api);
        }

        @Test
        public void testExSimdDot() {
            testDot(api);
        }

        @Test
        @Disabled
        public void testExSimdCross() {
            testCross(api);
        }
    }

    @Nested
    public class GenSimdVector4ApiTest {
        // Allow calling overloaded methods
        private final FVector4API api = new FVector4API();

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
        public void testGenSimdMul() {
            testMul(api);
        }

        @Test
        public void testGenSimdMulScalar() {
            testMulScalar(api);
        }

        @Test
        public void testGenSimdDot() {
            testDot(api);
        }

        @Test
        @Disabled
        public void testGenSimdCross() {
            testCross(api);
        }
    }

    protected void testEqual(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var b = new FVector4(1f, 2f, 3f, 4f);
        assertTrue(api.equals(a, b));
    }

    protected void testNotEquals(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var b = new FVector4(5f, 6f, 7f, 8f);
        assertFalse(api.equals(a, b));
    }

    protected void testAdd(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var b = new FVector4(8f, 5f, 7f, 6f);
        var r = new FVector4();
        api.add(a, b, r);
        assertArrayEquals(new float[]{
                9f, 7f, 10f, 10f
        }, r.data, MathUtils.TOLERANCE);
    }

    protected void testAddScalar(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var r = new FVector4();
        api.add(a, 3f, r);
        assertArrayEquals(new float[]{
                4f, 5f, 6f, 7f
        }, r.data, MathUtils.TOLERANCE);
    }

    protected void testSub(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var b = new FVector4(8f, 5f, 7f, 6f);
        var r = new FVector4();
        api.sub(a, b, r);
        assertArrayEquals(new float[]{
                -7f, -3f, -4f, -2f
        }, r.data, MathUtils.TOLERANCE);
    }

    protected void testSubScalar(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var r = new FVector4();
        api.sub(a, 3f, r);
        assertArrayEquals(new float[]{
                -2f, -1f, 0f, 1f
        }, r.data, MathUtils.TOLERANCE);
    }

    protected void testMul(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var b = new FVector4(8f, 5f, 7f, 6f);
        var r = new FVector4();
        api.mul(a, b, r);
        assertArrayEquals(new float[]{
                8f, 10f, 21f, 24f
        }, r.data, MathUtils.TOLERANCE);
    }

    protected void testMulScalar(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var r = new FVector4();
        api.mul(a, 3f, r);
        assertArrayEquals(new float[]{
                3f, 6f, 9f, 12f
        }, r.data, MathUtils.TOLERANCE);
    }

    protected void testDot(IFVectorApi<FVector4> api) {
        var a = new FVector4(1f, 2f, 3f, 4f);
        var b = new FVector4(8f, 5f, 7f, 6f);
        assertEquals(63f, api.dot(a, b), MathUtils.TOLERANCE);
    }

    protected void testCross(IFVectorApi<FVector4> api) {
        throw new UnsupportedOperationException("not implemented");
//        var a = new FVector4(1f, 2f, 3f);
//        var b = new FVector4(4f, 5f, 6f);
//        var c = new FVector4(4f, 5f, 6f);
//        var r = new FVector4();
//        api.cross(a, b, c, r);
//        assertArrayEquals(new float[]{
//                -3f, 6f, -3f
//        }, r.data, MathUtils.TOLERANCE);
    }

//    protected void testNormSquared(IFVectorApi<FVector4> api) {
//        var a = new FVector4(1f, 2f, 3f, 4f);
//
//        assertEquals(14f, api. a.normSquared(), MathUtils.TOLERANCE);
//    }
//
//    protected void testNorm(IFVectorApi<FVector4> api) {
//        var a = new FVector4(1f, 2f, 3f, 4f);
//
//        assertEquals(3.741657f, a.norm(), MathUtils.TOLERANCE);
//    }
}
