package LinkedIn.CA1;

import java.util.*;
// todo: check efficiency
public class AllOneMinMax {
    // O(1) operation:
    // map add, remove()
    // List add, remove(index)
    // maybe use a reverseMap to store CountWithString
    class Node {
        int freq;
        Set<String> keys;
        Node prev;
        Node next;
        Node(int freq) {
            this.freq = freq;
            keys = new HashSet<>();
        }
    }
    Map<String, Integer> keyWithCount;
    Map<Integer, Node> freqWithNode;
    Node head; // use for min
    Node tail; // use for max

    /** Initialize your data structure here. */
    public AllOneMinMax() {
        keyWithCount = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    private void addNodeInChain(Node cur, Node prev, Node next) {
        cur.prev = prev;
        cur.next = next;
        // not add in head
        if (prev != null) {
            prev.next = cur;
        } else {
            head = cur;
        }
        // not add in tail
        if (next != null) {
            next.prev = cur;
        } else {
            tail = cur;
        }
    }

    private void removeNodeInChain(Node cur) {
        Node prev = cur.prev;
        Node next = cur.next;
        // not remove in head
        if (prev != null) {
            prev.next = next;
        } else {
            next.prev = null;
            head = next;
        }
        // not remove in tail
        if (next != null) {
            next.prev = prev;
        } else {
            prev.next = null;
            tail = prev;
        }
    }
    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        Integer freq = keyWithCount.get(key);
        if (freq == null) {
            addKey(key);
        } else {
            updateAddKey(key, freq + 1);
        }
    }

    // add key to new node or existing node
    private void addKey(String key) {
        keyWithCount.put(key, 1);
        Node node = freqWithNode.get(1);
        // add key to new node;
        if (node == null) {
            node = new Node(1);
            addNodeInChain(node, null, head);
            freqWithNode.put(1, node);
        }
        node.keys.add(key);
    }
    // update from small freq node to larger freq node
    private void updateAddKey(String key, int freq) {
        keyWithCount.put(key, freq);
        Node old = freqWithNode.get(freq - 1);
        old.keys.remove(key);
        Node cur = freqWithNode.get(freq);
        // node is not existing add new node;
        if (cur == null) {
            cur = new Node(freq);
            addNodeInChain(cur, old, old.next);
            freqWithNode.put(freq, cur);
        }
        cur.keys.add(key);
        // remove node in the middle
        if (old.keys.isEmpty()) {
            removeNodeInChain(old);
            freqWithNode.remove(freq - 1);
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        Integer freq = keyWithCount.get(key);
        if (freq == null) {

        } else if (freq == 1) {
            removeKey(key);
        } else {
            updateDeductKey(key, freq - 1);
        }

    }

    private void removeKey(String key) {
        keyWithCount.remove(key);
        Node cur = freqWithNode.get(1);
        cur.keys.remove(key);
        if (cur.keys.isEmpty()) {
            removeNodeInChain(cur);
            freqWithNode.remove(1);
        }
    }

    private void updateDeductKey(String key, int freq) {
        keyWithCount.put(key, freq);
        Node old = freqWithNode.get(freq + 1);
        old.keys.remove(key);
        Node cur = freqWithNode.get(freq);
        if (cur == null) {
            cur = new Node(freq);
            addNodeInChain(cur, old.prev, old);
            freqWithNode.put(freq, cur);
        }
        cur.keys.add(key);
        if (old.keys.isEmpty()) {
            removeNodeInChain(old);
            freqWithNode.remove(freq + 1);
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if (tail == null) {
            return "";
        }
        Iterator<String> tailItr = tail.keys.iterator();
        return tailItr.hasNext() ? tailItr.next() : "";
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if (head == null) {
            return "";
        }
        Iterator<String> headItr = head.keys.iterator();
        return headItr.hasNext() ? headItr.next() : "";
    }
}
