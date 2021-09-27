package ch.szclsb.orbis.math.pojo;

import ch.szclsb.orbis.math.IVector3f;
import ch.szclsb.orbis.math.MathUtils;

public class Vector3f implements IVector3f<Vector3f> {
    public static final Vector3f ZERO = new Vector3f(0f, 0f, 0f);
    public static final Vector3f ONE = new Vector3f(1f, 1f, 1f);
    public static final Vector3f XY = new Vector3f(1f, 1f, 0f);
    public static final Vector3f XZ = new Vector3f(1f, 0f, 1f);
    public static final Vector3f YZ = new Vector3f(0f, 1f, 1f);
    public static final Vector3f X = new Vector3f(1f, 0f, 0f);
    public static final Vector3f Y = new Vector3f(0f, 1f, 0f);
    public static final Vector3f Z = new Vector3f(0f, 0f, 1f);

    public final float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public Vector3f(Vector2f vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = 0;
    }

    @Override
    public Vector3f add(Vector3f vec) {
        return new Vector3f(
                x + vec.x,
                y + vec.y,
                z + vec.z
        );
    }

    @Override
    public Vector3f sub(Vector3f vec) {
        return new Vector3f(
                x - vec.x,
                y - vec.y,
                z - vec.z
        );
    }

    @Override
    public Vector3f times(Vector3f vec) {
        return new Vector3f(
                x * vec.x,
                y * vec.y,
                z * vec.z
        );
    }

    @Override
    public Vector3f times(float s) {
        return new Vector3f(
                x * s,
                y * s,
                z * s
        );
    }

    @Override
    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    @Override
    public float[] toArray() {
        return new float[] {x, y, z};
    }

    @Override
    public float normSquared() {
        return x * x + y * y + z * z;
    }

    @Override
    public float dot(Vector3f vec) {
        return x * vec.x + y * vec.y + z * vec.z;
    }

    @Override
    public Vector3f cross(Vector3f vec) {
        return new Vector3f(
                y * vec.z - z * vec.y,
                z * vec.x - x * vec.z,
                x * vec.y - y * vec.x
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector3f vec) {
            return MathUtils.isFloatEquals(x, vec.x) && MathUtils.isFloatEquals(y, vec.y)
                    && MathUtils.isFloatEquals(z, vec.z);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }
}
