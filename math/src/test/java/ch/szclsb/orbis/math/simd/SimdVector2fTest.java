package ch.szclsb.orbis.math.simd;

import ch.szclsb.orbis.math.MathUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("SIMD")
@Tag("Vector2f")
@Tag("UnitTest")
public class SimdVector2fTest {
    @Test
    public void testEquals() {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(1f, 2f);

        assertEquals(a, b);
    }

    @Test
    public void testToString() {
        var a = new Vector2f(1f, 2f);

        assertEquals("(1.000, 2.000)", a.toString());
    }

    @Test
    public void testAdd() {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(3f, 4f);

        assertEquals(new Vector2f(4f, 6f), a.add(b));
    }

    @Test
    public void testSubtract() {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(3f, 4f);

        assertEquals(new Vector2f(-2f, -2f), a.sub(b));
    }

    @Test
    public void testTimes() {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(3f, 4f);

        assertEquals(new Vector2f(3f, 8f), a.times(b));
    }

    @Test
    public void testScale() {
        var a = new Vector2f(1f, 2f);
        var s = 3f;

        assertEquals(new Vector2f(3f, 6f), a.times(s));
    }

    @Test
    public void testNormSquared() {
        var a = new Vector2f(1f, 2f);

        assertEquals(5f, a.normSquared(), MathUtils.TOLERANCE);
    }

    @Test
    public void testNorm() {
        var a = new Vector2f(1f, 2f);

        assertEquals(2.2360679f, a.norm(), MathUtils.TOLERANCE);
    }

    @Test
    public void testDot() {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(3f, 4f);

        assertEquals(11f, a.dot(b), MathUtils.TOLERANCE);
    }
}
