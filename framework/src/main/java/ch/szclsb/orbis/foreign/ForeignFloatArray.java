package ch.szclsb.orbis.foreign;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

public class ForeignFloatArray implements IAddressable {
    private final MemorySegment segment;

    public ForeignFloatArray(MemorySession session, int size) {
        this.segment = session.allocateArray(ValueLayout.JAVA_FLOAT, size);
    }

    public ForeignFloatArray(MemorySession session, float... elements) {
        this.segment = session.allocateArray(ValueLayout.JAVA_FLOAT, elements);
    }

    public MemoryAddress address() {
        return segment.address();
    }

    public int count() {
        return (int) (segment.byteSize() / 4);
    }

    public long byteSize() {
        return segment.byteSize();
    }

    public float get(int index) {
        return segment.get(ValueLayout.JAVA_FLOAT, index);
    }

    public void set(int index, float data) {
        segment.set(ValueLayout.JAVA_FLOAT, index, data);
    }

    public float[] toArray() {
        return segment.toArray(ValueLayout.JAVA_FLOAT);
    }
}
