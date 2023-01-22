package ch.szclsb.orbis.foreign;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

public class ForeignCharArray implements IAddressable {
    private final MemorySegment segment;

    public ForeignCharArray(MemorySession session, int size) {
        this.segment = session.allocateArray(ValueLayout.JAVA_CHAR, size);
    }

    public MemoryAddress getAddress() {
        return segment.address();
    }

    public int getSize() {
        return (int) (segment.byteSize() / 32);
    }

    public char get(int index) {
        return segment.get(ValueLayout.JAVA_CHAR, index);
    }

    public String getString() {
        return segment.getUtf8String(0);
    }

    public void set(int index, char data) {
        segment.set(ValueLayout.JAVA_CHAR, index, data);
    }
}
