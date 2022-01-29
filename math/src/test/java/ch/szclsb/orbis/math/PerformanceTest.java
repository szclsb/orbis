package ch.szclsb.orbis.math;

import ch.szclsb.orbis.processor.SimdMatrix;
import ch.szclsb.orbis.processor.SimdVec;
import jdk.incubator.vector.FloatVector;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;


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
            private final Class<FVector4> tClass = FVector4.class;
            private final IFVectorApi<FVector4> api = new PojoVectorApi<>(tClass);
            private final FVector4 a = new FVector4(1f, 2f, 3f, 4f);
            private final FVector4 b = new FVector4(-3f, -7f, -21f, -93f);
            private final float s = 97f;

            @Test
            public void testAdd() {
                var time = testApiAdd(api, a, b, createVector(tClass));
                print(api, tClass, "add", time);
            }

            @Test
            public void testAddScalar() {
                var time = testApiAdd(api, a, s, createVector(tClass));
                print(api, tClass, "add scalar", time);
            }

            @Test
            public void testSub() {
                var time = testApiSub(api, a, b, createVector(tClass));
                print(api, tClass, "sub", time);
            }

            @Test
            public void testSubScalar() {
                var time = testApiSub(api, a, s, createVector(tClass));
                print(api, tClass, "sub scalar", time);
            }

            @Test
            public void testMul() {
                var time = testApiMul(api, a, b, createVector(tClass));
                print(api, tClass, "mul", time);
            }

            @Test
            public void testMulScalar() {
                var time = testApiMul(api, a, s, createVector(tClass));
                print(api, tClass, "mul scalar", time);
            }

            @Test
            public void testDot() {
                var time = testApiDot(api, a, b);
                print(api, tClass, "dot", time);
            }
        }

        @Nested
        @Tag("Matrix4f")
        public class Matrix4fTest {
            private final Class<FMatrix4x4> tClass = FMatrix4x4.class;
            private final IFMatrixApi<FMatrix4x4> api = new PojoMatrixApi<>(tClass);
            private final FMatrix4x4 a = new FMatrix4x4(
                    4f, 8f, 3f, 1f,
                    15f, 6f, 7f, 12f,
                    9f, 2f, 11f, 9f,
                    5f, 3f, 7f, 2f
            );
            private final FMatrix4x4 b = new FMatrix4x4(
                    7f, 10f, 3f, 14f,
                    11f, 6f, 7f, 13f,
                    1, 4f, 15f, 5f,
                    12f, 8f, 2f, 9f
            );
            private final float s = 97f;

            @Test
            public void testAdd() {
                var time = testApiAdd(api, a, b, createMatrix(tClass));
                print(api, tClass, "add", time);
            }

            @Test
            public void testAddScalar() {
                var time = testApiAdd(api, a, s, createMatrix(tClass));
                print(api, tClass, "add scalar", time);
            }

            @Test
            public void testSub() {
                var time = testApiSub(api, a, b, createMatrix(tClass));
                print(api, tClass, "sub", time);
            }

            @Test
            public void testSubScalar() {
                var time = testApiSub(api, a, s, createMatrix(tClass));
                print(api, tClass, "sub scalar", time);
            }

            @Test
            public void testMul() {
                var time = testApiMul(api, a, b, createMatrix(tClass));
                print(api, tClass, "mul elem", time);
            }

            @Test
            public void testMulScalar() {
                var time = testApiMul(api, a, s, createMatrix(tClass));
                print(api, tClass, "mul scalar", time);
            }

            @Test
            public void testMulMat() {
                var time = testApiMulMat(api, a, b, createMatrix(tClass));
                print(api, tClass, "mul", time);
            }
        }
    }

    @Nested
    @Tag("EXTERNAL_SIMD")
    public class CustomSimdTest {
        @AfterAll
        public static void afterClass() {
            System.out.println();
        }

        @Nested
        @Tag("Vector4f")
        public class Vector4fTest {
            private final Class<FVector4> tClass = FVector4.class;
            private final IFVectorApi<FVector4> api = new ExternalSimdVectorApi<>(tClass, FloatVector.SPECIES_128);
            private final FVector4 a = new FVector4(1f, 2f, 3f, 4f);
            private final FVector4 b = new FVector4(-3f, -7f, -21f, -93f);
            private final float s = 97f;

            @Test
            public void testAdd() {
                var time = testApiAdd(api, a, b, createVector(tClass));
                print(api, tClass, "add", time);
            }

            @Test
            public void testAddScalar() {
                var time = testApiAdd(api, a, s, createVector(tClass));
                print(api, tClass, "add scalar", time);
            }

            @Test
            public void testSub() {
                var time = testApiSub(api, a, b, createVector(tClass));
                print(api, tClass, "sub", time);
            }

            @Test
            public void testSubScalar() {
                var time = testApiSub(api, a, s, createVector(tClass));
                print(api, tClass, "sub scalar", time);
            }

            @Test
            public void testMul() {
                var time = testApiMul(api, a, b, createVector(tClass));
                print(api, tClass, "mul", time);
            }

            @Test
            public void testMulScalar() {
                var time = testApiMul(api, a, s, createVector(tClass));
                print(api, tClass, "mul scalar", time);
            }

            @Test
            public void testDot() {
                var time = testApiDot(api, a, b);
                print(api, tClass, "dot", time);
            }
        }

        @Nested
        @Tag("Matrix4f")
        public class Matrix4fTest {
            private final Class<FMatrix4x4> tClass = FMatrix4x4.class;
            private final IFMatrixApi<FMatrix4x4> api = new ExternalSimdMatrixApi<>(tClass, FloatVector.SPECIES_128);
            private final FMatrix4x4 a = new FMatrix4x4(
                    4f, 8f, 3f, 1f,
                    15f, 6f, 7f, 12f,
                    9f, 2f, 11f, 9f,
                    5f, 3f, 7f, 2f
            );
            private final FMatrix4x4 b = new FMatrix4x4(
                    7f, 10f, 3f, 14f,
                    11f, 6f, 7f, 13f,
                    1, 4f, 15f, 5f,
                    12f, 8f, 2f, 9f
            );
            private final float s = 97f;

            @Test
            public void testAdd() {
                var time = testApiAdd(api, a, b, createMatrix(tClass));
                print(api, tClass, "add", time);
            }

            @Test
            public void testAddScalar() {
                var time = testApiAdd(api, a, s, createMatrix(tClass));
                print(api, tClass, "add scalar", time);
            }

            @Test
            public void testSub() {
                var time = testApiSub(api, a, b, createMatrix(tClass));
                print(api, tClass, "sub", time);
            }

            @Test
            public void testSubScalar() {
                var time = testApiSub(api, a, s, createMatrix(tClass));
                print(api, tClass, "sub scalar", time);
            }

            @Test
            public void testMul() {
                var time = testApiMul(api, a, b, createMatrix(tClass));
                print(api, tClass, "mul elem", time);
            }

            @Test
            public void testMulScalar() {
                var time = testApiMul(api, a, s, createMatrix(tClass));
                print(api, tClass, "mul scalar", time);
            }

            @Test
            public void testMulMat() {
                var time = testApiMulMat(api, a, b, createMatrix(tClass));
                print(api, tClass, "mul", time);
            }
        }
    }

    @Nested
    @Tag("INTERNAL_SIMD")
    public class SimdTest {
        @AfterAll
        public static void afterClass() {
            System.out.println();
        }

        @Nested
        @Tag("Vector4f")
        public class Vector4fTest {
            private final Class<FVector4> tClass = FVector4.class;
            private final IFVectorApi<FVector4> api = new FVector4API();
            private final FVector4 a = new FVector4(1f, 2f, 3f, 4f);
            private final FVector4 b = new FVector4(-3f, -7f, -21f, -93f);
            private final float s = 97f;

            @Test
            public void testAdd() {
                var time = testApiAdd(api, a, b, createVector(tClass));
                print(api, tClass, "add", time);
            }

            @Test
            public void testAddScalar() {
                var time = testApiAdd(api, a, s, createVector(tClass));
                print(api, tClass, "add scalar", time);
            }

            @Test
            public void testSub() {
                var time = testApiSub(api, a, b, createVector(tClass));
                print(api, tClass, "sub", time);
            }

            @Test
            public void testSubScalar() {
                var time = testApiSub(api, a, s, createVector(tClass));
                print(api, tClass, "sub scalar", time);
            }

            @Test
            public void testMul() {
                var time = testApiMul(api, a, b, createVector(tClass));
                print(api, tClass, "mul", time);
            }

            @Test
            public void testMulScalar() {
                var time = testApiMul(api, a, s, createVector(tClass));
                print(api, tClass, "mul scalar", time);
            }

            @Test
            public void testDot() {
                var time = testApiDot(api, a, b);
                print(api, tClass, "dot", time);
            }
        }

        @Nested
        @Tag("Matrix4f")
        public class Matrix4fTest {
            private final Class<FMatrix4x4> tClass = FMatrix4x4.class;
            private final IFMatrixApi<FMatrix4x4> api = new FMatrix4x4API();
            private final FMatrix4x4 a = new FMatrix4x4(
                    4f, 8f, 3f, 1f,
                    15f, 6f, 7f, 12f,
                    9f, 2f, 11f, 9f,
                    5f, 3f, 7f, 2f
            );
            private final FMatrix4x4 b = new FMatrix4x4(
                    7f, 10f, 3f, 14f,
                    11f, 6f, 7f, 13f,
                    1, 4f, 15f, 5f,
                    12f, 8f, 2f, 9f
            );
            private final float s = 97f;

            @Test
            public void testAdd() {
                var time = testApiAdd(api, a, b, createMatrix(tClass));
                print(api, tClass, "add", time);
            }

            @Test
            public void testAddScalar() {
                var time = testApiAdd(api, a, s, createMatrix(tClass));
                print(api, tClass, "add scalar", time);
            }

            @Test
            public void testSub() {
                var time = testApiSub(api, a, b, createMatrix(tClass));
                print(api, tClass, "sub", time);
            }

            @Test
            public void testSubScalar() {
                var time = testApiSub(api, a, s, createMatrix(tClass));
                print(api, tClass, "sub scalar", time);
            }

            @Test
            public void testMul() {
                var time = testApiMul(api, a, b, createMatrix(tClass));
                print(api, tClass, "mul elem", time);
            }

            @Test
            public void testMulScalar() {
                var time = testApiMul(api, a, s, createMatrix(tClass));
                print(api, tClass, "mul scalar", time);
            }

            @Test
            @Disabled
            public void testMulMat() {
                var time = testApiMulMat(api, a, b, createMatrix(tClass));
                print(api, tClass, "mul", time);
            }
        }
    }

    private static <T extends FVector> long testApiAdd(IFVectorApi<T> api, T a, T b, T r) {
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

    private static <T extends FMatrix> long testApiAdd(IFMatrixApi<T> api, T a, T b, T r) {
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

    private static <T extends FVector> long testApiAdd(IFVectorApi<T> api, T a, float s, T r) {
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

    private static <T extends FMatrix> long testApiAdd(IFMatrixApi<T> api, T a, float s, T r) {
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

    private static <T extends FVector> long testApiSub(IFVectorApi<T> api, T a, T b, T r) {
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

    private static <T extends FMatrix> long testApiSub(IFMatrixApi<T> api, T a, T b, T r) {
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

    private static <T extends FVector> long testApiSub(IFVectorApi<T> api, T a, float s, T r) {
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

    private static <T extends FMatrix> long testApiSub(IFMatrixApi<T> api, T a, float s, T r) {
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

    private static <T extends FVector> long testApiMul(IFVectorApi<T> api, T a, T b, T r) {
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

    private static <T extends FMatrix> long testApiMul(IFMatrixApi<T> api, T a, T b, T r) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            api._mul(a, b, r);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            api._mul(a, b, r);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            api._mul(a, b, r);
        }

        return end - start;
    }

    private static <T extends FVector> long testApiMul(IFVectorApi<T> api, T a, float s, T r) {
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

    private static <T extends FMatrix> long testApiMul(IFMatrixApi<T> api, T a, float s, T r) {
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

    private static <T extends FMatrix> long testApiMulMat(IFMatrixApi<T> api, T a, T b, T r) {
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

    private static <T extends FVector> long testApiDot(IFVectorApi<T> api, T a, T b) {
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

    private static <T extends FVector> T createVector(Class<T> tClass) {
        try {
            return tClass.getConstructor().newInstance();
        } catch (Exception e) {
            fail(e);
        }
        return null;
    }

    private static <T extends FMatrix> T createMatrix(Class<T> tClass) {
        try {
            return tClass.getConstructor().newInstance();
        } catch (Exception e) {
            fail(e);
        }
        return null;
    }

//    private static <T extends FVector> void create(Class<T> tClass, float ...args) {
//        try {
//            tClass.getConstructor(Stream.of(args).map(x -> float.class).toArray(Class[]::new)).newInstance(args);
//        } catch (Exception e) {
//            fail(e);
//        }
//    }

    private static <T extends FVector> void print(IFVectorApi<T> api, Class<T> tClass, String name, long time) {
        var size = tClass.getAnnotation(SimdVec.class).lanes();
        var className = api.getClass().getSimpleName();
        System.out.printf("%-22s lanes %2d  Operation %-12s x%.1e took %6.1f ns/op%n",
                className, size, name, (float) ITERATIONS, (time / (0.000001f * ITERATIONS)));
    }

    private static <T extends FMatrix> void print(IFMatrixApi<T> api, Class<T> tClass, String name, long time) {
        var annotation = tClass.getAnnotation(SimdMatrix.class);
        var className = api.getClass().getSimpleName();
        System.out.printf("%-22s rows %2d columns %2d  Operation %-12s x%.1e took %6.1f ns/op%n",
                className, annotation.rows(), annotation.columns(), name, (float) ITERATIONS, (time / (0.000001f * ITERATIONS)));
    }
}
