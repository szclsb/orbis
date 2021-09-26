package ch.szclsb.orbis.app;

import ch.szclsb.orbis.math.simd.Vector2f;

public class Traingle {
    public static void main(String[] args) {
        var a = new Vector2f(1f, 2f);
        var b = new Vector2f(5f, 9f);
        System.out.println(a.add(b));
    }
}
