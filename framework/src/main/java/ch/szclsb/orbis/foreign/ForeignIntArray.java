package ch.szclsb.orbis.foreign;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

public class ForeignIntArray implements IAddressable {
    private final MemorySegment segment;

    public ForeignIntArray(MemorySession session, int size) {
        this.segment = session.allocateArray(ValueLayout.JAVA_INT, size);
    }

    public ForeignIntArray(MemorySession session, int... elements) {
        this.segment = session.allocateArray(ValueLayout.JAVA_INT, elements);
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

    public int get(int index) {
        return segment.get(ValueLayout.JAVA_INT, index);
    }

    public void set(int index, int data) {
        segment.set(ValueLayout.JAVA_INT, index, data);
    }

    public int[] toArray() {
        return segment.toArray(ValueLayout.JAVA_INT);
    }
}
