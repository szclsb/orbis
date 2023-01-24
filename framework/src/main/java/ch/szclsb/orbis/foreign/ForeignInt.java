package ch.szclsb.orbis.foreign;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

public class ForeignInt implements IAddressable {
    private final MemorySegment segment;

    public ForeignInt(MemorySession session) {
        this.segment = session.allocate(ValueLayout.JAVA_INT);
    }

    public ForeignInt(MemorySession session, int i) {
        this(session);
        set(i);
    }

    @Override
    public MemoryAddress address() {
        return segment.address();
    }

    public int get() {
        return segment.get(ValueLayout.JAVA_INT, 0);
    }

    public void set(int i) {
        segment.set(ValueLayout.JAVA_INT, 0, i);
    }
}
