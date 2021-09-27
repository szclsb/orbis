package ch.szclsb.orbis.math.jni;

public class Matrix4f {
    static {
        System.load(System.getProperty("user.dir") + "/libs/orbis_matrix4.dll");
    }

    private static final int SIZE = 16;

    private static native boolean cEqualsC(float[] a, float[] b, float t);
    private static native void cAdd(float[] a, float[] b, float[] r);
    private static native void cSub(float[] a, float[] b, float[] r);
    private static native void cMul(float[] a, float[] b, float[] r);
    private static native void cMul(float[] a, float s, float[] r);
    private static native void cMulElem(float[] a, float[] b, float[] r);

    private final float[] data;

    public Matrix4f(float a11, float a12, float a13, float a14,
                   float a21, float a22, float a23, float a24,
                   float a31, float a32, float a33, float a34,
                   float a41, float a42, float a43, float a44) {
        this.data = new float[]{
                a11, a12, a13, a14,
                a21, a22, a23, a24,
                a31, a32, a33, a34,
                a41, a42, a43, a44
        };
    }

    public Matrix4f() {
        this.data = new float[SIZE];
    }

    public Matrix4f(float[] data) {
        if (data.length != SIZE) throw new RuntimeException("Invalid Matrix size");
        this.data = new float[SIZE];
        System.arraycopy(data, 0, this.data, 0, SIZE);
    }

    public Matrix4f(Matrix4f mat) {
        this.data = new float[SIZE];
        System.arraycopy(mat.data, 0, this.data, 0, SIZE);
    }

    public Matrix4f add(Matrix4f mat) {
        var result = new Matrix4f();
        cAdd(data, mat.data, result.data);
        return result;
    }

    public Matrix4f sub(Matrix4f mat) {
        var result = new Matrix4f();
        cSub(data, mat.data, result.data);
        return result;
    }

    public Matrix4f times(Matrix4f mat) {
        return times(mat, false);
    }

    public Matrix4f times(Matrix4f mat, boolean elementwise) {
        var result = new Matrix4f();
        if (!elementwise) {
            cMul(data, mat.data, result.data);
        } else {
            cMulElem(data, mat.data, result.data);
        }
        return result;
    }

    public Matrix4f times(float s) {
        var result = new Matrix4f();
        cMul(data, s, result.data);
        return result;
    }

    public float[] toArray() {
        var buffer = new float[SIZE];
        System.arraycopy(data, 0, buffer, 0, SIZE);
        return buffer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Matrix4f mat) {
            return cEqualsC(data, mat.data, 0.000001f);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("""
     [
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
       %.3f, %.3f, %.3f, %.3f
     ]
     """, data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8],
                data[9], data[10], data[11], data[12], data[13], data[14], data[15]);
    }

    public static final Matrix4f ID = new Matrix4f(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);

    public static Matrix4f createTranslation(float x, float y, float z) {
        return new Matrix4f(
                1f, 0f, 0f, x,
                0f, 1f, 0f, y,
                0f, 0f, 1f, z,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4f createTranslation(Vector3f vec) {
        return new Matrix4f(
                1f, 0f, 0f, vec.getX(),
                0f, 1f, 0f, vec.getY(),
                0f, 0f, 1f, vec.getZ(),
                0f, 0f, 0f, 1f
        );
    }

    // todo create trigonometric lookup tables

    public static final Matrix4f STD_ORTHOGONAL_PROJECTION = Matrix4f.createOrthogonalProjection(-1f, 1f, -1f, 1f, -1f, 10f);

    public static Matrix4f createRotation(Vector3f axis, float phi) {
        var sin = (float) Math.sin(phi);
        var cos = (float) Math.cos(phi);
        var cos1 = (1 - cos);
        var ax = axis.getX();
        var ay = axis.getY();
        var az = axis.getZ();
        return new Matrix4f(
                cos1 * ax * ax + cos, cos1 * ax * ay - sin * az, cos1 * ax * az + sin * ay, 0f,
                cos1 * ay * ax + sin * az, cos1 * ay * ay + cos, cos1 * ay * az - sin * ax, 0f,
                cos1 * az * ax - sin * ay, cos1 * az * ay + sin * ax, cos1 * az * az + cos, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4f createRotationX(float phi) {
        var sin = (float) Math.sin(phi);
        var cos = (float) Math.cos(phi);
        return new Matrix4f(
                1f, 0f, 0f, 0f,
                0f, cos, -sin, 0f,
                0f, sin, cos, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4f createRotationY(float phi) {
        var sin = (float) Math.sin(phi);
        var cos = (float) Math.cos(phi);
        return new Matrix4f(
                cos, 0f, sin, 0f,
                0f, 1f, 0f, 0f,
                -sin, 0f, cos, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4f createRotationZ(float phi) {
        var sin = (float) Math.sin(phi);
        var cos = (float) Math.cos(phi);
        return new Matrix4f(
                cos, -sin, 0f, 0f,
                sin, cos, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4f createScale(float sx, float sy, float sz) {
        return new Matrix4f(
                sx, 0f, 0f, 0f,
                0f, sy, 0f, 0f,
                0f, 0f, sz, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4f createScale(Vector3f s) {
        return new Matrix4f(
                s.getX(), 0f, 0f, 0f,
                0f, s.getY(), 0f, 0f,
                0f, 0f, s.getZ(), 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4f createOrthogonalProjection(float left, float right, float bottom, float top, float near, float far) {
        var rl = 1f / (right - left);
        var tb = 1f / (top - bottom);
        var fn = 1f / (far - near);
        return new Matrix4f(
                2f * rl, 0f, 0f, -(right + left) * rl,
                0f, 2f * tb, 0f, -(top + bottom) * tb,
                0f, 0f, -2f * fn, -(far + near) * fn,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4f createPerspectiveFieldOfView(float fov, float aspect, float near, float far) {
        //TODO: Implement
//    return new Matrix4(
//        0f, 0f, 0f, 0f,
//        0f, 0f, 0f, 0f,
//        0f, 0f, 0f, 0f,
//        0f, 0f, 0f, 1f
//    );
        return null;
    }
}
