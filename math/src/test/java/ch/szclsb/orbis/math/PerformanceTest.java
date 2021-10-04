package ch.szclsb.orbis.math;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@Tag("PerformanceTest")
public class PerformanceTest {
    public static final int LEAD_ITERATIONS = 100;
    public static final int FOLLOW_UP_ITERATIONS = 100;
    public static final int ITERATIONS = 10000000;

    // pojo
    private static final Class<ch.szclsb.orbis.math.pojo.Vector2f> pojoVector2fClass = ch.szclsb.orbis.math.pojo.Vector2f.class;
    private static final Class<ch.szclsb.orbis.math.pojo.Vector3f> pojoVector3fClass = ch.szclsb.orbis.math.pojo.Vector3f.class;
    private static final Class<ch.szclsb.orbis.math.pojo.Vector4f> pojoVector4fClass = ch.szclsb.orbis.math.pojo.Vector4f.class;
    private static final Class<ch.szclsb.orbis.math.pojo.Matrix4f> pojoMatrix4fClass = ch.szclsb.orbis.math.pojo.Matrix4f.class;

    // simd
    private static final Class<ch.szclsb.orbis.math.simd.Vector2f> simdVector2fClass = ch.szclsb.orbis.math.simd.Vector2f.class;
    private static final Class<ch.szclsb.orbis.math.simd.Vector3f> simdVector3fClass = ch.szclsb.orbis.math.simd.Vector3f.class;
    private static final Class<ch.szclsb.orbis.math.simd.Vector4f> simdVector4fClass = ch.szclsb.orbis.math.simd.Vector4f.class;
    private static final Class<ch.szclsb.orbis.math.simd.Matrix4f> simdMatrix4fClass = ch.szclsb.orbis.math.simd.Matrix4f.class;

    // jni
    private static final Class<ch.szclsb.orbis.math.jni.Vector2f> jniVector2fClass = ch.szclsb.orbis.math.jni.Vector2f.class;
    private static final Class<ch.szclsb.orbis.math.jni.Vector3f> jniVector3fClass = ch.szclsb.orbis.math.jni.Vector3f.class;
    private static final Class<ch.szclsb.orbis.math.jni.Vector4f> jniVector4fClass = ch.szclsb.orbis.math.jni.Vector4f.class;
    private static final Class<ch.szclsb.orbis.math.jni.Matrix4f> jniMatrix4fClass = ch.szclsb.orbis.math.jni.Matrix4f.class;

    @Nested
    @Tag("Vector2f")
    class Vector2fTest {
        @BeforeAll
        public static void initVectorShape() {
            System.out.println("--------------------------------");
            System.out.println("testing Vector2f\n");
        }

        private static <V extends IVector2f<V>> V createVector2f(Class<V> vClass, float x, float y) {
            try {
                return vClass.getConstructor(float.class, float.class).newInstance(x, y);
            } catch (Exception e) {
                fail(e);
            }
            return null;
        }

        @Nested
        class AddTest {
            private static <V extends IVector2f<V>> void testAdd(Class<V> vClass) {
                var a = createVector2f(vClass, 1f, 2f);
                var b = createVector2f(vClass, -3f, -7f);
                var time = testVectorAdd(a, b);
                print(vClass, "add", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testAdd(pojoVector2fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testAdd(simdVector2fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testAdd(jniVector2fClass);
            }
        }

        @Nested
        class SubTest {
            private static <V extends IVector2f<V>> void testSub(Class<V> vClass) {
                var a = createVector2f(vClass, 1f, 2f);
                var b = createVector2f(vClass, -3f, -7f);
                var time = testVectorSub(a, b);
                print(vClass, "sub", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testSub(pojoVector2fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testSub(simdVector2fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testSub(jniVector2fClass);
            }
        }

        @Nested
        class TimesTest {
            private static <V extends IVector2f<V>> void testTimes(Class<V> vClass) {
                var a = createVector2f(vClass, 1f, 2f);
                var b = createVector2f(vClass, -3f, -7f);
                var time = testVectorTimes(a, b);
                print(vClass, "times", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testTimes(pojoVector2fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testTimes(simdVector2fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testTimes(jniVector2fClass);
            }
        }

        @Nested
        class ScaleTest {
            private static <V extends IVector2f<V>> void testScale(Class<V> vClass) {
                var a = createVector2f(vClass, 1f, 2f);
                var s = -11f;
                var time = testVectorScale(a, s);
                print(vClass, "scale", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testScale(pojoVector2fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testScale(simdVector2fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testScale(jniVector2fClass);
            }
        }

        @Nested
        class DotTest {
            private static <V extends IVector2f<V>> void testDot(Class<V> vClass) {
                var a = createVector2f(vClass, 1f, 2f);
                var b = createVector2f(vClass, -3f, -7f);
                var time = testVectorDot(a, b);
                print(vClass, "dot", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testDot(pojoVector2fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testDot(simdVector2fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testDot(jniVector2fClass);
            }
        }
    }

    @Nested
    @Tag("Vector3f")
    class Vector3fTest {
        @BeforeAll
        public static void initVectorShape() {
            System.out.println("--------------------------------");
            System.out.println("testing Vector3f\n");
        }

        private static <V extends IVector3f<V>> V createVector3f(Class<V> vClass, float x, float y, float z) {
            try {
                return vClass.getConstructor(float.class, float.class, float.class).newInstance(x, y, z);
            } catch (Exception e) {
                fail(e);
            }
            return null;
        }

        @Nested
        class AddTest {
            private static <V extends IVector3f<V>> void testAdd(Class<V> vClass) {
                var a = createVector3f(vClass, 1f, 2f, 3f);
                var b = createVector3f(vClass, -3f, -7f, -21f);
                var time = testVectorAdd(a, b);
                print(vClass, "add", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testAdd(pojoVector3fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testAdd(simdVector3fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testAdd(jniVector3fClass);
            }
        }

        @Nested
        class SubTest {
            private static <V extends IVector3f<V>> void testSub(Class<V> vClass) {
                var a = createVector3f(vClass, 1f, 2f, 3f);
                var b = createVector3f(vClass, -3f, -7f, -21f);
                var time = testVectorSub(a, b);
                print(vClass, "sub", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testSub(pojoVector3fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testSub(simdVector3fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testSub(jniVector3fClass);
            }
        }

        @Nested
        class TimesTest {
            private static <V extends IVector3f<V>> void testTimes(Class<V> vClass) {
                var a = createVector3f(vClass, 1f, 2f, 3f);
                var b = createVector3f(vClass, -3f, -7f, -21f);
                var time = testVectorTimes(a, b);
                print(vClass, "times", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testTimes(pojoVector3fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testTimes(simdVector3fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testTimes(jniVector3fClass);
            }
        }

        @Nested
        class ScaleTest {
            private static <V extends IVector3f<V>> void testScale(Class<V> vClass) {
                var a = createVector3f(vClass, 1f, 2f, 3f);
                var b = createVector3f(vClass, -3f, -7f, -21f);
                var s = -11f;
                var time = testVectorScale(a, s);
                print(vClass, "scale", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testScale(pojoVector3fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testScale(simdVector3fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testScale(jniVector3fClass);
            }
        }

        @Nested
        class DotTest {
            private static <V extends IVector3f<V>> void testDot(Class<V> vClass) {
                var a = createVector3f(vClass, 1f, 2f, 3f);
                var b = createVector3f(vClass, -3f, -7f, -21f);
                var time = testVectorDot(a, b);
                print(vClass, "dot", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testDot(pojoVector3fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testDot(simdVector3fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testDot(jniVector3fClass);
            }
        }
    }

    @Nested
    @Tag("Vector4f")
    class Vector4fTest {
        @BeforeAll
        public static void initVectorShape() {
            System.out.println("--------------------------------");
            System.out.println("testing Vector4f\n");
        }

        private static <V extends IVector4f<V>> V createVector4f(Class<V> vClass, float x, float y, float z, float w) {
            try {
                return vClass.getConstructor(float.class, float.class, float.class, float.class).newInstance(x, y, z, w);
            } catch (Exception e) {
                fail(e);
            }
            return null;
        }

        @Nested
        class AddTest {
            private static <V extends IVector4f<V>> void testAdd(Class<V> vClass) {
                var a = createVector4f(vClass, 1f, 2f, 3f, 4f);
                var b = createVector4f(vClass, -3f, -7f, -21f, -93f);
                var time = testVectorAdd(a, b);
                print(vClass, "add", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testAdd(pojoVector4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testAdd(simdVector4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testAdd(jniVector4fClass);
            }
        }

        @Nested
        class SubTest {
            private static <V extends IVector4f<V>> void testSub(Class<V> vClass) {
                var a = createVector4f(vClass, 1f, 2f, 3f, 4f);
                var b = createVector4f(vClass, -3f, -7f, -21f, -93f);
                var time = testVectorSub(a, b);
                print(vClass, "sub", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testSub(pojoVector4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testSub(simdVector4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testSub(jniVector4fClass);
            }
        }

        @Nested
        class TimesTest {
            private static <V extends IVector4f<V>> void testTimes(Class<V> vClass) {
                var a = createVector4f(vClass, 1f, 2f, 3f, 4f);
                var b = createVector4f(vClass, -3f, -7f, -21f, -93f);
                var time = testVectorTimes(a, b);
                print(vClass, "times", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testTimes(pojoVector4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testTimes(simdVector4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testTimes(jniVector4fClass);
            }
        }

        @Nested
        class ScaleTest {
            private static <V extends IVector4f<V>> void testScale(Class<V> vClass) {
                var a = createVector4f(vClass, 1f, 2f, 3f, 4f);
                var s = -11f;
                var time = testVectorScale(a, s);
                print(vClass, "scale", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testScale(pojoVector4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testScale(simdVector4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testScale(jniVector4fClass);
            }
        }

        @Nested
        class DotTest {
            private static <V extends IVector4f<V>> void testDot(Class<V> vClass) {
                var a = createVector4f(vClass, 1f, 2f, 3f, 4f);
                var b = createVector4f(vClass, -3f, -7f, -21f, -93f);
                var time = testVectorDot(a, b);
                print(vClass, "dot", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testDot(pojoVector4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testDot(simdVector4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testDot(jniVector4fClass);
            }
        }
    }

    @Nested
    @Tag("Matrix4f")
    class Matrix4fTest {
        @BeforeAll
        public static void initVectorShape() {
            System.out.println("--------------------------------");
            System.out.println("testing Matrix4f\n");
        }

        private static <M extends IMat4f<M>> M createMatrix4f(Class<M> vClass,
                                                              float a11, float a12, float a13, float a14,
                                                              float a21, float a22, float a23, float a24,
                                                              float a31, float a32, float a33, float a34,
                                                              float a41, float a42, float a43, float a44) {
            try {
                return vClass.getConstructor(
                        float.class, float.class, float.class, float.class,
                        float.class, float.class, float.class, float.class,
                        float.class, float.class, float.class, float.class,
                        float.class, float.class, float.class, float.class).newInstance(
                                a11, a12, a13, a14,
                                a21, a22, a23, a24,
                                a31, a32, a33, a34,
                                a41, a42, a43, a44);
            } catch (Exception e) {
                fail(e);
            }
            return null;
        }

        @Nested
        class AddTest {
            private static <M extends IMat4f<M>> void testAdd(Class<M> mClass) {
                var A = createMatrix4f(mClass,
                        1f, 2.3f, 33f, 14f,
                        3f, 21f, 1330f, -150f,
                        -8.1f, 0f, 97f, 4f,
                        12f, -4f, 453f, 197346f
                );
                var B = createMatrix4f(mClass,
                        -10f, -2f, -3f, 4f,
                        1.2f, 204146f, 91f, 785f,
                        0f, 2671f, -3.3f, -468f,
                        1478000f, -2.5f, -4121f, 697f
                );
                var time = testMatAdd(A, B);
                print(mClass, "add", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testAdd(pojoMatrix4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testAdd(simdMatrix4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testAdd(jniMatrix4fClass);
            }
        }

        @Nested
        class SubTest {
            private static <M extends IMat4f<M>> void testSub(Class<M> mClass) {
                var A = createMatrix4f(mClass,
                        1f, 2.3f, 33f, 14f,
                        3f, 21f, 1330f, -150f,
                        -8.1f, 0f, 97f, 4f,
                        12f, -4f, 453f, 197346f
                );
                var B = createMatrix4f(mClass,
                        -10f, -2f, -3f, 4f,
                        1.2f, 204146f, 91f, 785f,
                        0f, 2671f, -3.3f, -468f,
                        1478000f, -2.5f, -4121f, 697f
                );
                var time = testMatSub(A, B);
                print(mClass, "sub", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testSub(pojoMatrix4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testSub(simdMatrix4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testSub(jniMatrix4fClass);
            }
        }

        @Nested
        class TimesTest {
            private static <M extends IMat4f<M>> void testTimes(Class<M> mClass) {
                var A = createMatrix4f(mClass,
                        1f, 2.3f, 33f, 14f,
                        3f, 21f, 1330f, -150f,
                        -8.1f, 0f, 97f, 4f,
                        12f, -4f, 453f, 197346f
                );
                var B = createMatrix4f(mClass,
                        -10f, -2f, -3f, 4f,
                        1.2f, 204146f, 91f, 785f,
                        0f, 2671f, -3.3f, -468f,
                        1478000f, -2.5f, -4121f, 697f
                );
                var time = testMatTimes(A, B);
                print(mClass, "times", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testTimes(pojoMatrix4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testTimes(simdMatrix4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testTimes(jniMatrix4fClass);
            }
        }

        @Nested
        class TimesElemTest {
            private static <M extends IMat4f<M>> void testTimesElem(Class<M> mClass) {
                var A = createMatrix4f(mClass,
                        1f, 2.3f, 33f, 14f,
                        3f, 21f, 1330f, -150f,
                        -8.1f, 0f, 97f, 4f,
                        12f, -4f, 453f, 197346f
                );
                var B = createMatrix4f(mClass,
                        -10f, -2f, -3f, 4f,
                        1.2f, 204146f, 91f, 785f,
                        0f, 2671f, -3.3f, -468f,
                        1478000f, -2.5f, -4121f, 697f
                );
                var time = testMatTimesElem(A, B);
                print(mClass, "times elementwise", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testTimesElem(pojoMatrix4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testTimesElem(simdMatrix4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testTimesElem(jniMatrix4fClass);
            }
        }

        @Nested
        class ScaleTest {
            private static <M extends IMat4f<M>> void testScale(Class<M> mClass) {
                var A = createMatrix4f(mClass,
                        1f, 2.3f, 33f, 14f,
                        3f, 21f, 1330f, -150f,
                        -8.1f, 0f, 97f, 4f,
                        12f, -4f, 453f, 197346f
                );
                var s = 97f;
                var time = testMatScale(A, s);
                print(mClass, "scale", time);
            }

            @Test
            @Tag("POJO")
            public void testPojo() {
                testScale(pojoMatrix4fClass);
            }

            @Test
            @Tag("SIMD")
            public void testSimd() {
                testScale(simdMatrix4fClass);
            }

            @Test
            @Tag("JNI")
            public void testJni() {
                testScale(jniMatrix4fClass);
            }
        }
    }

//    private static <V extends IVector2f<V>> ArrayList<V> createVectors2f(Class<V> vClass, int size) {
//        try {
//            var result = new ArrayList<V>(size);
//            var rnd = new Random();
//            var constructor = vClass.getConstructor(float.class, float.class);
//            for (var i = 0; i < size; i++) {
//                result.add(constructor.newInstance(rnd.nextFloat(), rnd.nextFloat()));
//            }
//            return result;
//        } catch (Exception e) {
//            fail(e);
//        }
//        return null;
//    }

    private static <V extends IVector<V>> long testVectorAdd(V a, V b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.add(b);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.add(b);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.add(b);
        }

        return end - start;
    }

    private static <V extends IVector<V>> long testVectorSub(V a, V b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.sub(b);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.sub(b);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.sub(b);
        }

        return end - start;
    }

    private static <V extends IVector<V>> long testVectorTimes(V a, V b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.times(b);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.times(b);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.times(b);
        }

        return end - start;
    }

    private static <V extends IVector<V>> long testVectorScale(V a, float s) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.times(s);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.times(s);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.times(s);
        }

        return end - start;
    }

    private static <V extends IVector<V>> long testVectorDot(V a, V b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.dot(b);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.dot(b);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.dot(b);
        }

        return end - start;
    }

    private static <M extends IMat4f<M>> long testMatAdd(M a, M b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.add(b);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.add(b);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.add(b);
        }

        return end - start;
    }

    private static <M extends IMat4f<M>> long testMatSub(M a, M b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.sub(b);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.sub(b);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.sub(b);
        }

        return end - start;
    }

    private static <M extends IMat4f<M>> long testMatTimes(M a, M b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.times(b);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.times(b);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.times(b);
        }

        return end - start;
    }

    private static <M extends IMat4f<M>> long testMatTimesElem(M a, M b) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.times(b, true);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.times(b, true);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.times(b, true);
        }

        return end - start;
    }

    private static <M extends IMat4f<M>> long testMatScale(M a, float s) {
        for(var i = 0; i < LEAD_ITERATIONS; i++) {
            a.times(s);
        }
        var start = System.currentTimeMillis();
        for(var i = 0; i < ITERATIONS; i++) {
            a.times(s);
        }
        var end = System.currentTimeMillis();
        for(var i = 0; i < FOLLOW_UP_ITERATIONS; i++) {
            a.times(s);
        }

        return end - start;
    }

    private static void print(Class<?> vClass, String name, long time) {
        var packageName = vClass.getPackageName();
        var innerPkgName = packageName.substring(packageName.lastIndexOf('.'));
        var className = vClass.getSimpleName();
        System.out.printf("%s %s operation %s x10e6 took %d ms%n", innerPkgName, className, name, time);
        System.out.printf("%s %s Operation %s avg   took %f.3 ms%n", innerPkgName, className, name, ((1f * time) / ITERATIONS));
    }
}
