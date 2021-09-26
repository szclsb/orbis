package ch.szclsb.orbis.math.pojo;

import ch.szclsb.orbis.math.IVector4f;
import ch.szclsb.orbis.math.MathUtils;

public class Vector4f implements IVector4f<Vector4f> {
    public static final Vector4f ZERO = new Vector4f(0f, 0f, 0f, 0f);
    public static final Vector4f ONE = new Vector4f(1f, 1f, 1f, 1f);

    public final float x, y, z, w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(Vector4f vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        this.w = vec.w;
    }

    public Vector4f(Vector3f vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        this.w = 0;
    }

    @Override
    public Vector4f add(Vector4f vec) {
        return new Vector4f(
                x + vec.x,
                y + vec.y,
                z + vec.z,
                w + vec.w
        );
    }

    @Override
    public Vector4f sub(Vector4f vec) {
        return new Vector4f(
                x - vec.x,
                y - vec.y,
                z - vec.z,
                w - vec.w
        );
    }

    @Override
    public Vector4f mul(Vector4f vec) {
        return new Vector4f(
                x * vec.x,
                y * vec.y,
                z * vec.z,
                w * vec.w
        );
    }

    @Override
    public Vector4f mul(float s) {
        return new Vector4f(
                x * s,
                y * s,
                z * s,
                w * s
        );
    }

    @Override
    public float dot(Vector4f vector) {
        return x * vector.x + y * vector.y + z * vector.z + w * vector.w;
    }

    @Override
    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    @Override
    public float[] toArray() {
        return new float[]{x, y, z, w};
    }

    @Override
    public float normSquared() {
        return x * x + y * y + z * z + w * w;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Vector3f persDiv() {
        float dw = 1 / w;
        return new Vector3f(x * dw, y * dw, z * dw);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector4f vec) {
            return MathUtils.isFloatEquals(x, vec.x) && MathUtils.isFloatEquals(y, vec.y)
                    && MathUtils.isFloatEquals(z, vec.z) && MathUtils.isFloatEquals(w, vec.w);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f, %.3f)", x, y, z, w);
    }
}
