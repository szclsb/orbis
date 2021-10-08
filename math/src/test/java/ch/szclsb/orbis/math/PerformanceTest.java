package ch.szclsb.orbis.math;

import jdk.incubator.vector.FloatVector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("PerformanceTest")
public class PerformanceTest {
    public static final int LEAD_ITERATIONS = 10000;
    public static final int FOLLOW_UP_ITERATIONS = 1000;
    public static final int ITERATIONS = 10000000;

    @Nested
    @Tag("POJO")
    public class PojoTest {
        @AfterAll
        public static void afterClass() {
            System.out.println();
        }

        @Nested
        @Tag("Vector4f")
        public class Vector4fTest {
            private final int SIZE = 4;
            private final VectorAPI api = new PojoVectorApi(SIZE);
            private final float[] a = new float[]{1f, 2f, 3f, 4f};
            private final float[] b = new float[]{-3f, -7f, -21f, -93f};
            private final float s = 97f;

            @Test
            public void testAdd() {
                var time = testApiAdd(api, a, b, new float[SIZE]);
                print(api, SIZE, "add", time);
            }

            @Test
            public void testAddScalar() {
                var time = testApiAdd(api, a, s, new float[SIZE]);
                print(api, SIZE, "add scalar", time);
            }

            @Test
            public void testSub() {
                var time = testApiSub(api, a, b, new float[SIZE]);
                print(api, SIZE, "sub", time);
            }

            @Test
            public void testSubScalar() {
                var time = testApiSub(api, a, s, new float[SIZE]);
                print(api, SIZE, "sub scalar", time);
            }

            @Test
            public void testMul() {
                var time = testApiMul(api, a, b, new float[SIZE]);
                print(api, SIZE, "mul", time);
            }

            @Test
            public void testMulScalar() {
                var time = testApiMul(api, a, s, new float[SIZE]);
                print(api, SIZE, "mul scalar", time);
            }

            @Test
            public void testDot() {
                var time = testApiDot(api, a, b);
                print(api, SIZE, "dot", time);
            }
        }
    }

    @Nested
    @Tag("SIMD")
    public class SimdTest {
        @AfterAll
        public static void afterClass() {
            System.out.println();
        }

        @Nested
        @Tag("Vector4f")
        public class Vector4fTest {
            private final int SIZE = 4;
            private final VectorAPI api = new SimdVectorApi(FloatVector.SPECIES_128, SIZE);
            private final float[] a = new float[]{1f, 2f, 3f, 4f};
            private final float[] b = new float[]{-3f, -7f, -21f, -93f};
            private final float s = 97f;

            @Test
            public void testAdd() {
                var time = testApiAdd(api, a, b, new float[SIZE]);
                print(api, SIZE, "add", time);
            }

            @Test
            public void testAddScalar() {
                var time = testApiAdd(api, a, s, new float[SIZE]);
                print(api, SIZE, "add scalar", time);
            }

            @Test
            public void testSub() {
                var time = testApiSub(api, a, b, new float[SIZE]);
                print(api, SIZE, "sub", time);
            }

            @Test
            public void testSubScalar() {
                var time = testApiSub(api, a, s, new float[SIZE]);
                print(api, SIZE, "sub scalar", time);
            }

            @Test
            public void testMul() {
                var time = testApiMul(api, a, b, new float[SIZE]);
                print(api, SIZE, "mul", time);
            }

            @Test
            public void testMulScalar() {
                var time = testApiMul(api, a, s, new float[SIZE]);
                print(api, SIZE, "mul scalar", time);
            }

            @Test
            public void testDot() {
                var time = testApiDot(api, a, b);
                print(api, SIZE, "dot", time);
            }
        }
    }

    private static long testApiAdd(VectorAPI api, float[] a, float[] b, float[] r) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            api.add(a, b, r);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            api.add(a, b, r);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            api.add(a, b, r);
        }

        return end - start;
    }

    private static long testApiAdd(VectorAPI api, float[] a, float s, float[] r) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            api.add(a, s, r);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            api.add(a, s, r);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            api.add(a, s, r);
        }

        return end - start;
    }

    private static long testApiSub(VectorAPI api, float[] a, float[] b, float[] r) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            api.sub(a, b, r);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            api.sub(a, b, r);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            api.sub(a, b, r);
        }

        return end - start;
    }

    private static long testApiSub(VectorAPI api, float[] a, float s, float[] r) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            api.sub(a, s, r);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            api.sub(a, s, r);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            api.sub(a, s, r);
        }

        return end - start;
    }

    private static long testApiMul(VectorAPI api, float[] a, float[] b, float[] r) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            api.mul(a, b, r);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            api.mul(a, b, r);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            api.mul(a, b, r);
        }

        return end - start;
    }

    private static long testApiMul(VectorAPI api, float[] a, float s, float[] r) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            api.mul(a, s, r);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            api.mul(a, s, r);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            api.mul(a, s, r);
        }

        return end - start;
    }

    private static long testApiDot(VectorAPI api, float[] a, float[] b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            api.dot(a, b);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            api.dot(a, b);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            api.dot(a, b);
        }

        return end - start;
    }

    private static void print(VectorAPI api, int size, String name, long time) {
        var className = api.getClass().getSimpleName();
//        System.out.printf("%-4s %-8s operation %-20s x10e6 took %-8d ms%n", innerPkgName, className, name, time);
        System.out.printf("%-16s size %2d Operation %-20s x%.1e took %8.1f ns/op%n",
                className, size, name, (float) ITERATIONS, (time / (0.000001f * ITERATIONS)));
    }
}
