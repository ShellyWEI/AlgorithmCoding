package LinkedIn.CA1;

import java.util.*;
/**
 *
 * */
public class AllOneRandNoDup<T> {
    Map<T, Integer> reverseIndexMap = new HashMap<>();
    // TODO: What if resize time-complexity?
    List<T> index = new ArrayList<>();

    public void add(T val) {
        index.add(val);
        reverseIndexMap.put(val, index.size() - 1);
    }

    public void remove(T val) {
        Integer removedValueIndex = reverseIndexMap.get(val);
        reverseIndexMap.remove(val);
        // use last position in list to make up the removed position
        int lastIndex = index.size() - 1;
        T lastVal = index.get(lastIndex);
        index.add(removedValueIndex, lastVal);
        index.remove(lastIndex);
        // remember to update last value's position in map
        reverseIndexMap.put(lastVal, removedValueIndex);
    }
    public T removeRandomElement(){
        int randIndex = (int)(Math.random() * reverseIndexMap.size());
        T randEle = index.get(randIndex);
        remove(randEle);
        return randEle;
    }
}
