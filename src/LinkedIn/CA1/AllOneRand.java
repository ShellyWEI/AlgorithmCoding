package LinkedIn.CA1;

import java.util.*;

public class AllOneRand<T> {
    Map<T, Integer> reverseIndexMap = new HashMap<>();
    // TODO: What if resize time-complexity?
    List<T> index = new ArrayList<>();

    public void add(T val) {
        index.add(val);
        reverseIndexMap.put(val, index.size() - 1);
    }

    public void remove(T val) {
        Integer prevIndex = reverseIndexMap.get(val);
        reverseIndexMap.remove(val);
        int lastIndex = index.size() - 1;
        T lastVal = index.get(lastIndex);
        index.add(prevIndex, lastVal);
        T value = index.remove(lastIndex);
    }
    public T removeRandomElement(){
        int randIndex = (int)(Math.random() * reverseIndexMap.size());
        T randEle = index.get(randIndex);
        remove(randEle);
        return randEle;
    }
    PriorityQueue<T> heap = new PriorityQueue<>();
}
