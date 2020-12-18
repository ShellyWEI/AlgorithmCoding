package LinkedIn.CA1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class HashTableImp<K, V> {
    class Node {
        K key;
        V value;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private final int bucketNum = 100;
    private final double loadFactor = 0.75;
    List<LinkedList<Node>> buckets; // keys with according buckets
    List<ReentrantLock> locks = new ArrayList<>(bucketNum); // lock on one key with all the buckets

    HashTableImp() {
        buckets = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            locks.add(new ReentrantLock());
        }
    }

    LinkedList<Node> lockEachBucket(K key) {
        if (key == null) {
            return null;
        }
        int index = getIndex(key);
        locks.get(index).lock();
        LinkedList<Node> pairs = buckets.get(index);
        if (pairs == null) {
            pairs = new LinkedList<>();
            buckets.set(index, pairs); //????
        }
        return pairs;
    }

    void unLockEachBucket(K key) {
        int index = getIndex(key);
        locks.get(index).unlock();
    }

    private int getIndex(K key) {
        // hashcode 可能是负数，&后保证正数
        return (key.hashCode() & 0x7FFFFFFF) % bucketNum;
    }

    public V get(K key) {
        try {
            LinkedList<Node> nodesOfBucket = lockEachBucket(key);
            for (Node node : nodesOfBucket) {
                if (equals(node.key, key)) {
                    return node.value;
                }
            }
            nodesOfBucket.removeIf(node -> node.key == key);
            return null;
        } finally {
            unLockEachBucket(key);
        }
    }

    public void put(K key, V value) {
        try {
            LinkedList<Node> nodesOfBucket = lockEachBucket(key);
            // update existing key
            for (Node node : nodesOfBucket) {
                if (equals(node.key, key)) {
                    node.value = value;
                    return;
                }
            }
            // if not have existing one, add new
            nodesOfBucket.add(new Node(key, value));
        } finally {
            unLockEachBucket(key);
        }
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(K key) {
        int index = getIndex(key);
        ReentrantLock lock = locks.get(index);
        lock.lock();
        try {
            LinkedList<Node> nodes = buckets.get(index);
            nodes.removeIf(node -> node.key == key);
        } finally {
            lock.unlock();
        }
    }

    private boolean equals(K key1, K key2) {
        if (key1 == null && key2 == null) {
            return true;
        } else if (key1 != null && key2 != null) {
            return key1.equals(key2);
        } else {
            return false;
        }
    }
}
