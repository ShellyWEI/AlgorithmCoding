package Lyft;

import java.util.HashMap;
import java.util.Map;

abstract class Stream {
    abstract int read(int n);
}
public class MultiStreamRead {
    StreamNode head;
    StreamNode tail;
    StreamNode curRead;
    Map<Stream, StreamNode> map;

    class StreamNode {
        Stream stream;
        StreamNode prev;
        StreamNode next;
        StreamNode (Stream s) {
            this.stream = s;
        }
    }
    public MultiStreamRead() {
        this.head = null;
        this.tail = null;
        this.map = new HashMap<>();
    }

    public void add(Stream s) {
        StreamNode newNode = new StreamNode(s);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void remove(Stream s) {
        if (!map.containsKey(s)) {
            return;
        }
        StreamNode toBeRemoved = map.get(s);
        if (toBeRemoved == curRead) {
            curRead = toBeRemoved.next;
        }
        if (head == toBeRemoved) {
            if (head == tail) {
                head = null;
                tail = null;
                return;
            }
            head = toBeRemoved.next;
            head.prev = null;
        } else if (tail == toBeRemoved) {
            tail = toBeRemoved.prev;
            tail.next = null;
        } else {
            StreamNode prev = toBeRemoved.prev;
            prev.next = toBeRemoved.next;
            toBeRemoved.next.prev = prev;
        }
    }
    // only need to record the stream position, stream itself will record previous read positions
    public int read (int n) {
        // either newStream or continued from last time
        Stream curStream = curRead.stream;
        // n is remaining size to be read
        int actualRead = 0;
        while (n > 0) {
            // two scenarios:
            // 1. read full n bytes (might reach end)
            // 2. read < n and reach end
            int readInStream = curStream.read(n);
            n -= readInStream;
            actualRead += readInStream;
            if (n == 0) {
                break;
            }
            if (readInStream == 0) {
                curRead = curRead.next;
                // no stream left to be read
                if (curRead == null) {
                    break;
                }
                curStream = curRead.stream;
            }
        }
        return actualRead;
    }
}
