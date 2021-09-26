package ch.szclsb.orbis.math.simd;

import ch.szclsb.orbis.math.MathUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector3fTest {
    @Test
    public void testEquals() {
        var a = new Vector3f(1f, 2f, 3f);
        var b = new Vector3f(1f, 2f, 3f);

        assertEquals(a, b);
    }

    @Test
    public void testToString() {
        var a = new Vector3f(1f, 2f, 3f);

        assertEquals("(1.000, 2.000, 3.000)", a.toString());
    }

    @Test
    public void testAdd() {
        var a = new Vector3f(1f, 2f, 3f);
        var b = new Vector3f(4f, 5f, 6f);

        assertEquals(new Vector3f(5f, 7f, 9f), a.add(b));
    }

    @Test
    public void testSubtract() {
        var a = new Vector3f(1f, 2f, 3f);
        var b = new Vector3f(4f, 5f, 6f);

        assertEquals(new Vector3f(-3f, -3f, -3f), a.sub(b));
    }

    @Test
    public void testMultiply() {
        var a = new Vector3f(1f, 2f, 3f);
        var b = new Vector3f(4f, 5f, 6f);

        assertEquals(new Vector3f(4f, 10f, 18f), a.mul(b));
    }

    @Test
    public void testScale() {
        var a = new Vector3f(1f, 2f, 3f);
        var s = 4f;

        assertEquals(new Vector3f(4f, 8f, 12f), a.mul(s));
    }

    @Test
    public void testNormSquared() {
        var a = new Vector3f(1f, 2f, 3f);

        assertEquals(14f, a.normSquared(), MathUtils.TOLERANCE);
    }

    @Test
    public void testNorm() {
        var a = new Vector3f(1f, 2f, 3f);

        assertEquals(3.741657f, a.norm(), MathUtils.TOLERANCE);
    }

    @Test
    public void testDot() {
        var a = new Vector3f(1f, 2f, 3f);
        var b = new Vector3f(4f, 5f, 6f);

        assertEquals(32f, a.dot(b), MathUtils.TOLERANCE);
    }

    @Test
    @Disabled
    public void testCross() {
        var a = new Vector3f(1f, 2f, 3f);
        var b = new Vector3f(4f, 5f, 6f);

        assertEquals(new Vector3f(-3f, 6f, -3f), a.cross(b));
    }

//    @Test
//    public void testToHomogenous() {
//        var a = new Vector3f(1f, 2f, 3f);
//
//        assertEquals(new Vector3f(1f, 2f, 3f, 1f), a.toHomogenous());
//    }
}
