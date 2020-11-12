package Google;

import java.util.*;

public class LRUCache {
    Map<Integer, Data> map;
    Data head;
    Data tail;
    private int limit;

    class Data {
        private int key;
        private int value;
        Data prev;
        Data next;

        public Data(int key, int value) {
            this.key = key;
            this.value = value;
            prev = null;
            next = null;
        }

        public void updateValue(int newValue) {
            value = newValue;
        }

        public int getKey() {
            return this.key;
        }
    }

    public LRUCache(int capacity) {
        this.limit = capacity;
        map = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    public void put(int key, int value) {
        Data data = map.get(key);
        // if key exited, modify it
        if (data != null) {
            data.updateValue(value);
            if (head.getKey() == key) {
                if (head != tail) {
                    removeHead();
                    addTail(data);
                }
            } else if (tail.getKey() != key) {
                removeMiddle(data);
                addTail(data);
            }
        } else {
            // key doesn't exist and have to new Data
            Data newdata = new Data(key, value);
            // have to evict
            if (map.size() >= limit) {
                map.remove(head.key);
                removeHead();
                addTail(newdata);
            } else {
                addTail(newdata);
            }
            map.put(key, newdata);
        }
    }

    private void removeMiddle(Data data) {
        Data prev = data.prev;
        Data next = data.next;
        prev.next = next;
        next.prev = prev;
    }

    private void removeHead() {
        if (head.next != null) {
            Data newHead = head.next;
            newHead.prev = null;
            head.next = null;
            head = newHead;
        } else {
            head = null;
        }
    }

    private void addTail(Data newTail) {
        newTail.next = null;
        if (tail != null) {
            tail.next = newTail;
            newTail.prev = tail;
        }
        tail = newTail;
        if (head == null) {
            head = newTail;
        }
    }

    public int get(int key) {
        Data data = map.get(key);
        if (data == null) {
            return -1;
        }

            if (head.getKey() == key) {
                removeHead();
                addTail(data);
            } else if (tail.getKey() != key) {
                removeMiddle(data);
                addTail(data);
            }
        return data.value;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
    }
}
