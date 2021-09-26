package ch.szclsb.orbis.math.pojo;

import ch.szclsb.orbis.math.MathUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector4fTest {
    @Test
    public void testEquals() {
        var a = new Vector4f(1f, 2f, 3f, 4f);
        var b = new Vector4f(1f, 2f, 3f, 4f);

        assertEquals(a, b);
    }

    @Test
    public void testToString() {
        var a = new Vector4f(1f, 2f, 3f, 4f);

        assertEquals("(1.000, 2.000, 3.000, 4.000)", a.toString());
    }

    @Test
    public void testAdd() {
        var a = new Vector4f(1f, 2f, 3f, 4f);
        var b = new Vector4f(5f, 6f, 7f, 8f);

        assertEquals(new Vector4f(6f, 8f, 10f, 12f), a.add(b));
    }

    @Test
    public void testSubtract() {
        var a = new Vector4f(1f, 2f, 3f, 4f);
        var b = new Vector4f(5f, 6f, 7f, 8f);

        assertEquals(new Vector4f(-4f, -4f, -4f, -4f), a.sub(b));
    }

    @Test
    public void testMultiply() {
        var a = new Vector4f(1f, 2f, 3f, 4f);
        var b = new Vector4f(5f, 6f, 7f, 8f);

        assertEquals(new Vector4f(5f, 12f, 21f, 32f), a.mul(b));
    }

    @Test
    public void testScale() {
        var a = new Vector4f(1f, 2f, 3f, 4f);
        var s = 5f;

        assertEquals(new Vector4f(5f, 10f, 15f, 20f), a.mul(s));
    }

    @Test
    public void testNormSquared() {
        var a = new Vector4f(1f, 2f, 3f, 4f);

        assertEquals(30f, a.normSquared(), MathUtils.TOLERANCE);
    }

    @Test
    public void testNorm() {
        var a = new Vector4f(1f, 2f, 3f, 4f);

        assertEquals(5.477226f, a.norm(), MathUtils.TOLERANCE);
    }

    @Test
    public void testPerspectiveDivision() {
        var a = new Vector4f(16f, 8f, 12f, 4f);

        assertEquals(new Vector3f(4f, 2f, 3f), a.persDiv());
    }
}
