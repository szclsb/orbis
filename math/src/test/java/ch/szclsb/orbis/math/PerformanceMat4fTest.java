package ch.szclsb.orbis.math;

import ch.szclsb.orbis.math.pojo.Matrix4f;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("POJO")
@Tag("Matrix4f")
@Tag("PerformanceTest")
public class PerformanceMat4fTest {
    @BeforeAll
    static void start() {
        System.out.println("--------------------------------");
        System.out.println("Testing Pojo Matrix4f\n");
    }

    @Test
    public void testMatrix4fAdd() {
        var A = new Matrix4f(
                1f, 2.3f, 33f, 14f,
                3f, 21f, 1330f, -150f,
                -8.1f, 0f, 97f, 4f,
                12f, -4f, 453f, 197346f
        );
        var B = new Matrix4f(
                -10f, -2f, -3f, 4f,
                1.2f, 204146f, 91f, 785f,
                0f, 2671f, -3.3f, -468f,
                1478000f, -2.5f, -4121f, 697f
        );
        // lead time
        for(var i = 0; i < 100; i++) {
            A.add(B);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            A.add(B);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            A.add(B);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            A.add(B);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            A.add(B);
        }

        System.out.println("Matrix4f add x10     took " + (time2 - time1) + " ms");
        System.out.println("Matrix4f add x1000   took " + (time3 - time2) + " ms");
        System.out.println("Matrix4f add x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }

    @Test
    public void testMatrix4fSub() {
        var A = new Matrix4f(
                1f, 2.3f, 33f, 14f,
                3f, 21f, 1330f, -150f,
                -8.1f, 0f, 97f, 4f,
                12f, -4f, 453f, 197346f
        );
        var B = new Matrix4f(
                -10f, -2f, -3f, 4f,
                1.2f, 204146f, 91f, 785f,
                0f, 2671f, -3.3f, -468f,
                1478000f, -2.5f, -4121f, 697f
        );
        // lead time
        for(var i = 0; i < 100; i++) {
            A.sub(B);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            A.sub(B);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            A.sub(B);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            A.sub(B);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            A.sub(B);
        }

        System.out.println("Matrix4f sub x10     took " + (time2 - time1) + " ms");
        System.out.println("Matrix4f sub x1000   took " + (time3 - time2) + " ms");
        System.out.println("Matrix4f sub x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }

    @Test
    public void testMatrix4fTimesElem() {
        var A = new Matrix4f(
                1f, 2.3f, 33f, 14f,
                3f, 21f, 1330f, -150f,
                -8.1f, 0f, 97f, 4f,
                12f, -4f, 453f, 197346f
        );
        var B = new Matrix4f(
                -10f, -2f, -3f, 4f,
                1.2f, 204146f, 91f, 785f,
                0f, 2671f, -3.3f, -468f,
                1478000f, -2.5f, -4121f, 697f
        );
        // lead time
        for(var i = 0; i < 100; i++) {
            A.times(B, true);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            A.times(B, true);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            A.times(B, true);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            A.times(B, true);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            A.times(B, true);
        }

        System.out.println("Matrix4f times elementwise x10     took " + (time2 - time1) + " ms");
        System.out.println("Matrix4f times elementwise x1000   took " + (time3 - time2) + " ms");
        System.out.println("Matrix4f times elementwise x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }

    @Test
    public void testMatrix4fTimesScalar() {
        var A = new Matrix4f(
                1f, 2.3f, 33f, 14f,
                3f, 21f, 1330f, -150f,
                -8.1f, 0f, 97f, 4f,
                12f, -4f, 453f, 197346f
        );
        var B = 2.5f;
        // lead time
        for(var i = 0; i < 100; i++) {
            A.times(B);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            A.times(B);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            A.times(B);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            A.times(B);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            A.times(B);
        }

        System.out.println("Matrix4f times scalar x10     took " + (time2 - time1) + " ms");
        System.out.println("Matrix4f times scalar x1000   took " + (time3 - time2) + " ms");
        System.out.println("Matrix4f times scalar x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }

    @Test
    public void testMatrix4fTimes() {
        var A = new Matrix4f(
                1f, 2.3f, 33f, 14f,
                3f, 21f, 1330f, -150f,
                -8.1f, 0f, 97f, 4f,
                12f, -4f, 453f, 197346f
        );
        var B = new Matrix4f(
                -10f, -2f, -3f, 4f,
                1.2f, 204146f, 91f, 785f,
                0f, 2671f, -3.3f, -468f,
                1478000f, -2.5f, -4121f, 697f
        );
        // lead time
        for(var i = 0; i < 100; i++) {
            A.times(B);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            A.times(B);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            A.times(B);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            A.times(B);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            A.times(B);
        }

        System.out.println("Matrix4f times x10     took " + (time2 - time1) + " ms");
        System.out.println("Matrix4f times x1000   took " + (time3 - time2) + " ms");
        System.out.println("Matrix4f times x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }
}
