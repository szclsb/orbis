package ch.szclsb.orbis.math.pojo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Tag("POJO")
@Tag("Matrix4f")
@Tag("UnitTest")
public class Mat4fTest {
    @Test
    public void testEquals() {
        var A = new Matrix4f(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f);

        var B = new Matrix4f(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f);

        assertEquals(A, B);
    }

    @Test
    public void testFalseEquals() {
        var A = new Matrix4f(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f);

        var B = new Matrix4f(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 0f, 12f,
                13f, 14f, 15f, 16f);

        assertNotEquals(A, B);
    }

    @Test
    public void testToString() {
        var A = new Matrix4f(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f);

        assertEquals("""
                [
                  1.000, 2.000, 3.000, 4.000
                  5.000, 6.000, 7.000, 8.000
                  9.000, 10.000, 11.000, 12.000
                  13.000, 14.000, 15.000, 16.000
                ]
                """, A.toString());
    }

    @Test
    public void testAdd() {
        var A = new Matrix4f(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );

        var B = new Matrix4f(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );

        assertEquals(new Matrix4f(
                11f, 18f, 6f, 15f,
                26f, 12f, 14f, 25f,
                10f, 6f, 26f, 14f,
                17f, 11f, 9f, 11f
        ), A.add(B));
    }

    @Test
    public void testSub() {
        var A = new Matrix4f(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );

        var B = new Matrix4f(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );

        assertEquals(new Matrix4f(
                -3f, -2f, 0f, -13f,
                4f, 0f, 0f, -1f,
                8f, -2f, -4f, 4f,
                -7f, -5f, 5f, -7f
        ), A.sub(B));
    }

    @Test
    public void testTimes() {
        var A = new Matrix4f(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );

        var B = new Matrix4f(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );

        assertEquals(new Matrix4f(
                131f, 108f, 115f, 184f,
                322f, 310f, 216f, 431f,
                204f, 218f, 224f, 288f,
                99f, 112f, 145f, 162f
        ), A.times(B));
    }

    @Test
    public void testTimesElements() {
        var A = new Matrix4f(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );

        var B = new Matrix4f(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );

        assertEquals(new Matrix4f(
                28f, 80f, 9f, 14f,
                165f, 36f, 49f, 156f,
                9f, 8f, 165f, 45f,
                60f, 24f, 14f, 18f
        ), A.times(B, true));
    }

    @Test
    public void testTimesScalar() {
        var A = new Matrix4f(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        );

        assertEquals(new Matrix4f(
                12f, 24, 9f, 3f,
                45f, 18f, 21f, 36f,
                27f, 6f, 33f, 27f,
                15f, 9f, 21f, 6f
        ), A.times(3f));
    }
}
