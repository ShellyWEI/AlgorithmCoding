package LinkedIn.CA2;

import java.util.*;
// todo: check efficiency
public class AllOneMinMax {
    // O(1) operation:
    // map add, remove()
    // List add, remove(index)
    // maybe use a reverseMap to store CountWithString
    ListNode head;
    ListNode tail;
    Map<String, Integer> key2Value;
    Map<Integer, ListNode> value2Node;

    class ListNode {
        ListNode next;
        ListNode prev;
        Set<String> keys;
        int value;
        ListNode(int value) {
            this.keys = new HashSet<>();
            this.value = value;
        }
    }

    private void removeFromQueue(ListNode node) {
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    // cur: node to be added
    // prev: value smaller than cur
    // next: value larget than cur
    private void addToQueue(ListNode prev, ListNode cur, ListNode next) {
        cur.prev = prev;
        cur.next = next;
        // handle head
        if (prev != null) {
            prev.next = cur;
        } else {
            head = cur;
        }
        // handle tail
        if (next != null) {
            next.prev = cur;
        } else {
            tail = cur;
        }
    }

    // add new key in the queue;
    private void add(String key) {
        key2Value.put(key, 1);
        ListNode node = value2Node.get(1);
        if (node == null) {
            node = new ListNode(1);
            addToQueue(null, node, head);
            value2Node.put(1, node);
        }
        node.keys.add(key);
    }

    // remove key in the queue
    private void remove(String key) {
        key2Value.remove(key);
        ListNode node = value2Node.get(1);
        if (node.keys.size() == 1) {
            removeFromQueue(node);
            value2Node.remove(1);
        } else {
            node.keys.remove(key);
        }
    }

    // update existing key and move position in the queue
    private void updateKey(String key, int oldValue, int newValue) {
        key2Value.put(key, newValue);

        ListNode oldNode = value2Node.get(oldValue);
        oldNode.keys.remove(key);

        ListNode newNode = value2Node.get(newValue);
        if (newNode == null) {
            newNode = new ListNode(newValue);
            value2Node.put(newValue, newNode);
            if (oldValue < newValue) {
                addToQueue(oldNode, newNode, oldNode.next);
            } else {
                addToQueue(oldNode.prev, newNode, oldNode);
            }

        }
        newNode.keys.add(key);

        if (oldNode.keys.isEmpty()) {
            removeFromQueue(oldNode);
            value2Node.remove(oldValue);
        }
    }

    /** Initialize your data structure here. */
    public AllOneMinMax() {
        head = null;
        tail = null;
        key2Value = new HashMap<>();
        value2Node = new HashMap<>();
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        Integer value = key2Value.get(key);
        if (value == null) {
            add(key);
        } else {
            updateKey(key, value, value + 1);
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        Integer value = key2Value.get(key);
        if (value == null) {
            return;
        } else if (value == 1) {
            remove(key);
        } else {
            updateKey(key, value, value - 1);
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
