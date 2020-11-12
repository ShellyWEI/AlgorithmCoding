package Amazon.Onsite;

import java.util.*;

/**
 * Hierholzer's algorithm:
 * DFS: for each node's unvisited neighbor:
 *  mark visited
 *  if no unvisited nodes:
 *  deque.offerLeft() <-- the end would be first found*/
public class ItineraryConstruction {
    public List<String> findItinerary(List<List<String>> tickets) {
        // use priorityQueue to maintain lexical order
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        // build map
        for (List<String> path : tickets) {
            PriorityQueue<String> tos = map.get(path.get(0));
            if (tos == null) {
                tos = new PriorityQueue<>();
                map.put(path.get(0), tos);
            }
            tos.offer(path.get(1));
        }
        LinkedList<String> res = new LinkedList<>();
        DFS(map, res, "JFK");
        return res;
    }
    private void DFS(Map<String, PriorityQueue<String>> map, LinkedList<String> res, String from) {
        PriorityQueue<String> unvisited = map.get(from);
        // end of itinerary
        if (unvisited == null) {
            res.offerFirst(from);
            return;
        }
        while (!unvisited.isEmpty()) {
            String to = unvisited.poll();
            DFS(map, res, to);
        }
        // if from don't have any unvisited children
        // that's usually how a node was put in to res in reverse order, so we use offerLeft here
        res.offerFirst(from);
    }
}
