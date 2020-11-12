package Lyft;

import java.util.*;

public class VersionedKVStore {
    private int versionNumber;
    Map<Integer, Map<Integer, Integer>> key2ValueWithVersion;

    public VersionedKVStore() {
        this.versionNumber = 0;
        this.key2ValueWithVersion = new HashMap<>();
    }

    /**
     * Each put operation would increase version number
     */
    public void put (int key, int value) {
        versionNumber++;
        Map<Integer, Integer> version2Value = key2ValueWithVersion.computeIfAbsent(key, k -> new HashMap<>());
        version2Value.put(versionNumber, value);
        StringBuilder sb = new StringBuilder();
    }
    /**
     * Return the most recent version's value
     * */
    public Integer get(int key) {
        return getInVersion(key, versionNumber);
    }
    /**
     * Return value with specific version
     * */
    public Integer getInVersion(int key, int version) {
        Map<Integer, Integer> version2Value = key2ValueWithVersion.get(key);
        if (version2Value == null) {
            return null;
        }
        if (version > this.versionNumber) {
            version = versionNumber;
        }
        return version2Value.get(version);
    }
}
