package ch.szclsb.orbis.foreign;

import java.io.IOException;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.nio.file.Files;
import java.nio.file.Path;

public class ForeignString implements IAddressable {
    private final MemorySegment segment;

    public ForeignString(MemorySession session, String data) {
        this.segment = session.allocateUtf8String(data);
    }

    public ForeignString(MemorySession session, Path path) throws IOException {
        // todo load file natively
        this.segment = session.allocateUtf8String(Files.readString(path));
    }

    @Override
    public MemoryAddress getAddress() {
        return segment.address();
    }

    public String get() {
        return segment.getUtf8String(0);
    }
}
