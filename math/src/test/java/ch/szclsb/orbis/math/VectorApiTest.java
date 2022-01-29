package ch.szclsb.orbis.math;

import jdk.incubator.vector.FloatVector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@Tag("UnitTest")
public class VectorApiTest {
    @Nested
    public class PojoVector3ApiTest {
        private final PojoVectorApi<FVector3> api = new PojoVectorApi<>(FVector3.class);

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
    public class ExSimdVector3ApiTest {
        private final ExternalSimdVectorApi<FVector3> api = new ExternalSimdVectorApi<>(FVector3.class, FloatVector.SPECIES_128);

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
    public class GenSimdVector3ApiTest {
        // Allow calling overloaded methods
        private final FVector3API api = new FVector3API();

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

    protected void testEqual(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var b = new FVector3(1f, 2f, 3f);
        assertTrue(api.equals(a, b));
    }

    protected void testNotEquals(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var b = new FVector3(4f, 5f, 6f);
        assertFalse(api.equals(a, b));
    }

    protected void testAdd(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var b = new FVector3(4f, 5f, 6f);
        var r = new FVector3();
        api.add(a, b, r);
        assertArrayEquals(new float[]{
                5f, 7f, 9f
        }, r.data);
    }

    protected void testAddScalar(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var r = new FVector3();
        api.add(a, 3f, r);
        assertArrayEquals(new float[]{
                4f, 5f, 6f
        }, r.data);
    }

    protected void testSub(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var b = new FVector3(4f, 5f, 6f);
        var r = new FVector3();
        api.sub(a, b, r);
        assertArrayEquals(new float[]{
                -3f, -3f, -3f
        }, r.data);
    }

    protected void testSubScalar(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var r = new FVector3();
        api.sub(a, 3f, r);
        assertArrayEquals(new float[]{
                -2f, -1f, 0f
        }, r.data);
    }

    protected void testMul(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var b = new FVector3(4f, 5f, 6f);
        var r = new FVector3();
        api.mul(a, b, r);
        assertArrayEquals(new float[]{
                4f, 10f, 18f
        }, r.data);
    }

    protected void testMulScalar(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var r = new FVector3();
        api.mul(a, 3f, r);
        assertArrayEquals(new float[]{
                3f, 6f, 9f
        }, r.data);
    }

    protected void testDot(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var b = new FVector3(4f, 5f, 6f);
        assertEquals(32f, api.dot(a, b), MathUtils.TOLERANCE);
    }

    protected void testCross(IFVectorApi<FVector3> api) {
        var a = new FVector3(1f, 2f, 3f);
        var b = new FVector3(4f, 5f, 6f);
        var r = new FVector3();
        api.cross(a, b, r);
        assertArrayEquals(new float[]{
                -3f, 6f, -3f
        }, r.data, MathUtils.TOLERANCE);
    }

//    protected void testNormSquared(IFVectorApi<FVector3> api) {
//        var a = new FVector3(1f, 2f, 3f);
//
//        assertEquals(14f, api. a.normSquared(), MathUtils.TOLERANCE);
//    }
//
//    protected void testNorm(IFVectorApi<FVector3> api) {
//        var a = new Vector3f(1f, 2f, 3f);
//
//        assertEquals(3.741657f, a.norm(), MathUtils.TOLERANCE);
//    }
}
