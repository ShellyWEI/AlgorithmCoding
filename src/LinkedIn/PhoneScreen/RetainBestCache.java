package LinkedIn.PhoneScreen;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// don't implement these two
interface Rankable {
    long getRank();
}

interface DataSource<K, T extends Rankable> {
    T get(K key);
}

class Data<K, T extends Rankable> implements DataSource<K, T> {
    K key;
    T data;

    public Data(K key, T data) {
        this.key = key;
        this.data = data;
    }

    @Override
    public T get(K key) {
        return data;
    }

}

public class RetainBestCache<K, T extends Rankable> {
    DataSource<K, T> ds;
    int cacheSize;

    Map<K, T> map; // store key value pair
    PriorityQueue<Data<K,T>> maxHeap; // rank

    // cache with capacity
    public RetainBestCache(DataSource<K, T> ds, int entriesToRetain) {
        this.ds = ds;
        cacheSize = entriesToRetain;
        map = new HashMap<>(entriesToRetain);
        maxHeap = new PriorityQueue<>(entriesToRetain, (o1, o2) ->  (int)(o1.data.getRank() - o2.data.getRank()));
    }

    // get data from cache, else fetch from datasource and cache it.
    // if cache is full, evict the lowest rank.
    public T get(K key) {
        T data = map.get(key);
        // get directly from cache;
        if (data != null) {
            return data;
        } else {
            // get from dataSource
            T newData = ds.get(key);
            map.put(key, newData);
            maxHeap.offer(new Data(key, newData));
            // cache is full, evict
            if (map.size() >= cacheSize) {
                Data<K,T> entry = maxHeap.poll();
                map.remove(entry.key);
            }
            return newData;
        }
    }



}
