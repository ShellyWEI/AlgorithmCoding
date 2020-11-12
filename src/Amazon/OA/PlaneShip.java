package Amazon.OA;

import java.util.*;
public class PlaneShip {
    private List<List<Integer>> forwardRouteList;
    private List<List<Integer>> returnRouteList;

    public List<List<Integer>> PrimeMaxProfit(int maxTravelDist,
                                               List<List<Integer>> forwardRouteList,
                                               List<List<Integer>> returnRouteList) {
        List<List<Integer>> res = new ArrayList<>();
        this.forwardRouteList = forwardRouteList;
        this.returnRouteList = returnRouteList;
        // minHeap has forwardRoute Index and returnRoute Index Pair
        PriorityQueue<List<Integer>> minHeap = new PriorityQueue<>(Comparator.comparing(this::sum));
        Comparator<List<Integer>> comparator = (o1, o2) -> {
            if (o1.get(1).equals(o2.get(1))) {
                return o1.get(0).compareTo(o2.get(0));
            }
            return o1.get(1) < (o2.get(1)) ? -1 : 1;
        };
        Collections.sort(forwardRouteList, comparator);
        Collections.sort(returnRouteList, comparator);
        for (int i = 0; i < forwardRouteList.size(); i++) {
            minHeap.offer(Arrays.asList(i, 0));
        }
        int prevSum = 0;
        while (!minHeap.isEmpty()) {
            List<Integer> curPair = minHeap.poll();
            int curSum = sum(curPair);
            if (curSum > maxTravelDist) {
                break;
            } else if (curSum > prevSum) {
                res.clear();
            }
            res.add(Arrays.asList(forwardRouteList.get(curPair.get(0)).get(0), returnRouteList.get(curPair.get(1)).get(0)));
            if (curPair.get(1) + 1 < returnRouteList.size()) {
                minHeap.offer(Arrays.asList(curPair.get(0), curPair.get(1) + 1));
            }
            prevSum = curSum;
        }
        return res;
    }
    public Integer sum(List<Integer> pair) {
        return forwardRouteList.get(pair.get(0)).get(1) + returnRouteList.get(pair.get(1)).get(1);
    }
    public static void main(String[] args) {
        List<List<Integer>> forward = new ArrayList<>();
        List<List<Integer>> returnL = new ArrayList<>();
        forward.add(Arrays.asList(1, 5000));
        forward.add(Arrays.asList(3, 4000));
        forward.add(Arrays.asList(2, 3000));
        forward.add(Arrays.asList(4, 1000));
        forward.add(Arrays.asList(5, 4000));
        returnL.add(Arrays.asList(1, 2000));
        returnL.add(Arrays.asList(3, 5000));
        returnL.add(Arrays.asList(2, 5000));
        returnL.add(Arrays.asList(4, 6000));
        List<List<Integer>> res = new PlaneShip().PrimeMaxProfit(9000, forward, returnL);
        System.out.println(res);
    }
}
