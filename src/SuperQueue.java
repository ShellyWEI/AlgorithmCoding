import java.util.concurrent.atomic.AtomicReference;

public class SuperQueue {
    AtomicReference<SuperNode> head;
    AtomicReference<SuperNode> tail;

    public SuperNode take() {
        SuperNode tmpHead = head.get();
        SuperNode newHead = tmpHead.next;
        while (!head.compareAndSet(tmpHead, newHead)) {
            tmpHead = head.get();
            newHead = tmpHead.next;
        }
        return tmpHead;
    }

    public void put(SuperNode newTail) {
        SuperNode curTail = tail.get();
        curTail.next = newTail;
        while (!tail.compareAndSet(curTail, newTail)) {
            curTail = tail.get();
            curTail.next = newTail;
        }
    }

    private class SuperNode {
        public SuperNode next;
    }
}
