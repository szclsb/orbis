package ch.szclsb.orbis.buffer;

import java.util.Objects;

public class VertexAttribute {
    private final ValueType type;
    private final int count;

    public VertexAttribute(ValueType type, int count) {
        this.type = type;
        this.count = count;
    }

    public ValueType type() {
        return this.type;
    }

    public int count() {
        return this.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, count);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VertexAttribute va) {
            return type.equals(va.type) && count == va.count;
        }
        return false;
    }
}
