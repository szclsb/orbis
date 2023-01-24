package ch.szclsb.orbis.foreign;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

public class ForeignLong implements IAddressable {
    private final MemorySegment segment;

    public ForeignLong(MemorySession session) {
        this.segment = session.allocate(ValueLayout.JAVA_LONG);
    }

    public ForeignLong(MemorySession session, long i) {
        this(session);
        set(i);
    }

    @Override
    public MemoryAddress address() {
        return segment.address();
    }

    public long get() {
        return segment.get(ValueLayout.JAVA_LONG, 0);
    }

    public void set(long i) {
        segment.set(ValueLayout.JAVA_LONG, 0, i);
    }
}
