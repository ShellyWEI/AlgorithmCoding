package Laioffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GraphNode {
     int key;
     List<GraphNode> neighbors;
     GraphNode(int key ){
         this.key = key;
         this.neighbors = new ArrayList<>();
     }
 }
public class DeepCopyGraph {
    public List<GraphNode> copy(List<GraphNode> graph) {
        // Write your solution here.
        Map<GraphNode, GraphNode> map = new HashMap<>();
        List<GraphNode> res = new ArrayList<>();
        for (GraphNode cur : graph) {
            GraphNode copied = map.get(cur);
            if (copied == null) {
                map.put(cur, new GraphNode(cur.key));
                DFS(cur, map);
            }
            res.add(copied);
        }
        return res;
    }
    // generate links
    private void DFS(GraphNode curNode, Map<GraphNode, GraphNode> map) {
        GraphNode copied = map.get(curNode);
        for (GraphNode nei : curNode.neighbors) {
            if (!map.containsKey(nei)) {
                map.put(nei, new GraphNode(nei.key));
            }
            copied.neighbors.add(map.get(nei));
        }
    }
}
