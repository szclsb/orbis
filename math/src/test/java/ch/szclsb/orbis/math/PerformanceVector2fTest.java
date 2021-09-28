package ch.szclsb.orbis.math;

import ch.szclsb.orbis.math.pojo.Vector2f;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("POJO")
@Tag("Vector2f")
@Tag("PerformanceTest")
public class PerformanceVector2fTest {
    @BeforeAll
    static void start() {
        System.out.println("--------------------------------");
        System.out.println("Testing Pojo Vector2f\n");
    }

    @Test
    public void testVector2fAdd() {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(1f, 2f);
        // lead time
        for(var i = 0; i < 100; i++) {
            a.add(b);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            a.add(b);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            a.add(b);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            a.add(b);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            a.add(b);
        }

        System.out.println("Vector2f add x10     took " + (time2 - time1) + " ms");
        System.out.println("Vector2f add x1000   took " + (time3 - time2) + " ms");
        System.out.println("Vector2f add x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }

    @Test
    public void testVector2fSub() {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(1f, 2f);
        // lead time
        for(var i = 0; i < 100; i++) {
            a.sub(b);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            a.sub(b);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            a.sub(b);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            a.sub(b);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            a.sub(b);
        }

        System.out.println("Vector2f sub x10     took " + (time2 - time1) + " ms");
        System.out.println("Vector2f sub x1000   took " + (time3 - time2) + " ms");
        System.out.println("Vector2f sub x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }

    @Test
    public void testVector2fTimes() {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(1f, 2f);
        // lead time
        for(var i = 0; i < 100; i++) {
            a.times(b);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            a.times(b);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            a.times(b);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            a.times(b);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            a.times(b);
        }

        System.out.println("Vector2f times x10     took " + (time2 - time1) + " ms");
        System.out.println("Vector2f times x1000   took " + (time3 - time2) + " ms");
        System.out.println("Vector2f times x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }

    @Test
    public void testVector2fScale() {
        var a = new Vector2f(1f, 2f);
        var b = 3f;
        // lead time
        for(var i = 0; i < 100; i++) {
            a.times(b);
        }
        // small iteration
        var time1 = System.currentTimeMillis();
        for(var i = 0; i < 10; i++) {
            a.times(b);
        }
        // medium iteration
        var time2 = System.currentTimeMillis();
        for(var i = 0; i < 1000; i++) {
            a.times(b);
        }
        // large iteration
        var time3 = System.currentTimeMillis();
        for(var i = 0; i < 100000; i++) {
            a.times(b);
        }
        var time4 = System.currentTimeMillis();
        // follow-up time
        for(var i = 0; i < 100; i++) {
            a.times(b);
        }

        System.out.println("Vector2f scale x10     took " + (time2 - time1) + " ms");
        System.out.println("Vector2f scale x1000   took " + (time3 - time2) + " ms");
        System.out.println("Vector2f scale x100000 took " + (time4 - time3) + " ms");
        System.out.println();
    }
}
