package Lyft.Onsite.KeyValue;

import Lyft.VersionedKVStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * input = """
 *     PUT key1 5
 *     PUT key2 6
 *     GET key1
 *     GET key1 1
 *     GET key2 2
 *     PUT key1 7
 *     GET key1 1
 *     GET key1 2
 *     GET key1 3
 *     GET key4
 *     GET key1 4
 *
 * output = """
 *     PUT(#1) key1 = 5
 *     PUT(#2) key2 = 6
 *     GET key1 = 5
 *     GET key1(#1) = 5
 *     GET key2(#2) = 6
 *     PUT(#3) key1 = 7
 *     GET key1(#1) = 5
 *     GET key1(#2) = 5
 *     GET key1(#3) = 7
 *     GET key4 = <NULL>
 *     GET key1(#4) = 7
 *      */
public class VersionedKeyValueStore<K, V> {
    enum OPERATION {
        GET,
        PUT,
    }
    Map<K, TreeMap<Integer, V>> key2Value;
    int version;
    VersionedKeyValueStore() {
        this.key2Value = new HashMap<>();
        this.version = 0;
    }
    /**
     * put key to associated treeMap<Version, V>
     * */
    private void put(K key, V value) {
        version++;
        TreeMap<Integer, V> version2Value = key2Value.computeIfAbsent(key, k -> new TreeMap<>());
        version2Value.put(version, value);
    }
    private V get(K key, int version) {
        TreeMap<Integer, V> version2Value = key2Value.get(key);
        if (version2Value == null) {
            return null;
        }
        // find greatest key in map
        Integer versionNumber = version2Value.floorKey(version);
        // the version don't have the value
        if (versionNumber == null) {
            return null;
        } else {
            return version2Value.get(versionNumber);
        }
    }
    public static void main(String[] args) {
        VersionedKeyValueStore keyValueStore = new VersionedKeyValueStore();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String[] line = sc.nextLine().split(" ");
            if (line.length < 2 || line.length > 3) {
                System.out.println("invalid input");
                continue;
            }
            int length = line.length;
            OPERATION operation = OPERATION.valueOf(line[0].toUpperCase());
            String key = line[1];
            switch (operation) {
                case GET:
                    int queryVersion;
                    if (length == 2) {
                        queryVersion = keyValueStore.version;
                    } else {
                        queryVersion = Integer.parseInt(line[2]);
                    }
                    if (length == 2) {
                        System.out.println("GET " + key + " = " + keyValueStore.get(key, queryVersion));
                    } else {
                        System.out.println("GET " + key + "(#" + queryVersion + ") = " + keyValueStore.get(key, queryVersion));
                    }
                    break;
                case PUT:
                    if (length == 2) {
                        System.out.println("invalid input");
                        continue;
                    }
                    keyValueStore.put(key, line[2]);
                    System.out.println("PUT(#"+ keyValueStore.version + ") " + key + " = " + line[2]);
                    break;
            }
        }
    }

}
