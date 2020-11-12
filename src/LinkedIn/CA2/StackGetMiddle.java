package LinkedIn.CA2;

import LinkedIn.CA1.DoublyListNode;

import java.util.NoSuchElementException;

public class StackGetMiddle {
    DoublyListNode head = null;
    DoublyListNode tail = null;
    DoublyListNode mid = null;
    int size = 0;

    public void push(int val) {
        DoublyListNode newNode = new DoublyListNode(val);
        if (head == null) {
            size++;
            head = newNode;
            tail = head;
            mid = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            size++;
            if (size % 2 == 1) {
                mid = mid.next;
            }
        }
    }
    public DoublyListNode pop() {
        if (head == null) {
            throw new NoSuchElementException();
        } else {
            DoublyListNode res = head;
            if (size == 1) {
                head = null;
                tail = null;
                mid = null;
                size = 0;
                return res;
            }
            DoublyListNode next = head.next;
            next.prev = null;
            head.next = null;
            head = next;
            size--;
            if (size % 2 == 0) {
                mid = mid.prev;
            }
            return res;
        }
    }

    public DoublyListNode getMid() {
        return mid;
    }
}
