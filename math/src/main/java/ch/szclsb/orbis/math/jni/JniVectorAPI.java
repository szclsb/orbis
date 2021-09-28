package ch.szclsb.orbis.math.jni;

public final class JniVectorAPI {
    static {
        System.load(System.getProperty("user.dir") + "/libs/orbis_native.dll");
    }

    public static native void cAdd(int size, float[] r, float[] a, float[] b);
    public static native void cSub(int size, float[] r, float[] a, float[] b);
    public static native void cMul(int size, float[] r, float[] a, float[] b);
    public static native void cMul(int size, float[] r, float[] a, float s);
    public static native float cDot(int size, float[] a, float[] b);
    public static native boolean cEquals(int size, float[] a, float[] b, float tol);
}
