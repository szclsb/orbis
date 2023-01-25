package ch.szclsb.orbis.buffer;

import java.util.Arrays;
import java.util.stream.Stream;

public class VertexLayout {
//    public static final VertexLayout FLOAT_2 = new VertexLayout(FLOAT, FLOAT);
//    public static final VertexLayout FLOAT_3 = new VertexLayout(FLOAT, FLOAT, FLOAT);
//    public static final VertexLayout FLOAT_4 = new VertexLayout(FLOAT, FLOAT, FLOAT, FLOAT);
//    public static final VertexLayout FLOAT_7 = new VertexLayout(FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT);
//    public static final VertexLayout FLOAT_9 = new VertexLayout(FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT, FLOAT);

    private final VertexAttribute[] attributes;

    public VertexLayout(VertexAttribute... attributes) {
        this.attributes = attributes;
    }

    public int count() {
        return this.attributes.length;
    }

    public VertexAttribute get(int index) {
        if (index < 0 || index >= attributes.length) {
            throw new IllegalArgumentException(String.format("index must be in [%d, %d)", 0, attributes.length));
        }
        return attributes[index];
    }

    public Stream<VertexAttribute> attributeStream() {
        return Arrays.stream(attributes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VertexLayout layout) {
            return Arrays.equals(attributes, layout.attributes);
        }
        return false;
    }
}
