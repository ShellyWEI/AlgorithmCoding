package LinkedIn.IDI2.DataStructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class WindowAvg {
    class Msg {
        long key; // unique
        long val;
        int timestamp;
    }
    Map<Long, Msg> map;
    Queue<Msg> queue;

    // define a window of a certain size in microsecond granularity
    WindowAvg(int nMicrosecs) {
        map = new HashMap<>();
        queue = new LinkedList<>();
    }

    // frequently
    void addMsg(Msg m) {
        queue.add(m);
        map.put(m.key, m);
    }

    // if msg does not exist or message is older than window size, return null
    Msg getMsg(long key) {

        return map.get(key);
    }

    // get average message of Msg values in the window
    Double getAvg() {

    }
}
