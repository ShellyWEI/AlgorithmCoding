package LinkedIn.CA1.FileSystem;

import java.util.Arrays;
import java.util.LinkedList;

public class FileSystemVariableSize extends pBuffer {
    private final int FIRST_BLOCK = BLOCK_COUNT / (BLOCK_SIZE * 8) + 1;
    private final byte[] empty = new byte[BLOCK_SIZE];
    private LinkedList<Integer> free = new LinkedList<>(); // for recording free block space available

    FileSystemVariableSize() {
        for (int i = FIRST_BLOCK; i <= BLOCK_COUNT; i++) {
            free.offerFirst(i);
        }
        Arrays.fill(empty, new Integer(0).byteValue());
    }
    private boolean isSet(int index) {
        int byteIndex = index / 8;
        int bitIndex = index % 8;

        int mask = 0x1 << bitIndex;

        return (buffer[byteIndex] & mask) != 0;
    }

    private void set(int index) {
        int byteIndex = index / 8;
        int bitIndex = index % 8;

        buffer[byteIndex] |= new Integer(0x1 << bitIndex).byteValue(); // why not use =, old bit can be exist
    }

    private void unset(int index) {
        int byteIndex = index / 8;
        int bitIndex = index % 8;

        buffer[byteIndex] &= ~new Integer(0x1 << bitIndex).byteValue();
    }

    @Override
    public Location create() throws NoAvailableSpaceException {
        if (free.isEmpty()) {
            throw new NoAvailableSpaceException();
        } else {
            int index = free.removeFirst();
            set(index);
            return new Location(index);
        }
    }

    @Override
    public void free(Location l) {
        int blockIndex = l.getLocation();
        if (!(blockIndex < FIRST_BLOCK || blockIndex >= BLOCK_COUNT) && isSet(blockIndex)) {
            unset(blockIndex);
            put(l, empty);
            free.offerFirst(blockIndex);
        }
    }

    @Override
    public void put(Location l, byte[] data) {
        int blockIndex = l.getLocation();
        if (!(blockIndex < FIRST_BLOCK || blockIndex >= BLOCK_COUNT || isSet(blockIndex))) {
            int bufferIndex = blockIndex * BLOCK_SIZE;
            System.arraycopy(data, 0, buffer, bufferIndex, BLOCK_SIZE);
        }
    }

    @Override
    public byte[] get(Location l) {
        int blockIndex = l.getLocation();
        if (!(blockIndex < FIRST_BLOCK || blockIndex >= BLOCK_COUNT) && isSet(blockIndex)) {
            int bufferIndex = blockIndex * BLOCK_SIZE;
            byte[] res = new byte[BLOCK_SIZE];
            System.arraycopy(buffer, bufferIndex, res, 0, BLOCK_SIZE);
            return res;
        }
        return null;
    }
}

class Location {
    int index; // index in pBuffer
    Location(int index) {
        this.index = index;
    }
    int getLocation() {
        return index;
    }
}

class NoAvailableSpaceException extends Exception {
    NoAvailableSpaceException() {
        System.out.println("No free Space left in Buffer");
    }
}


