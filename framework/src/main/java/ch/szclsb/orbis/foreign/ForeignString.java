package ch.szclsb.orbis.foreign;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

public class ForeignString implements IAddressable {
    private final MemorySegment segment;

    public ForeignString(MemorySession session, String data) {
        this.segment = session.allocateUtf8String(data);
    }

    @Override
    public MemoryAddress getAddress() {
        return segment.address();
    }

    public String get() {
        return segment.getUtf8String(0);
    }
}
