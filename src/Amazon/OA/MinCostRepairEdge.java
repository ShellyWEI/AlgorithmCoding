package Amazon.OA;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MinCostRepairEdge {
    public int repair(int n, int[][] edge, int[][] edgesToRepair) {
        Arrays.sort(edgesToRepair, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[2] == o2[2]) {
                    return 0;
                }
                return o1[2] < o2[2] ? -1 : 1;
            }
        });
        // record edge with min cost if duplicates
        Map<String, Integer> map = new HashMap<>();
        for (int[] edgeCost : edgesToRepair) {
            map.merge(Arrays.toString(Arrays.copyOfRange(edgeCost, 0, 2)), edgeCost[2], Math::min);
        }
        int components = n;
        int[] root = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            root[i] = i;
        }
        for (int[] e : edge) {
            if (!map.containsKey(Arrays.toString(e))) {
                if (union(e[0], e[1], root)) {
                    components--;
                }
            }
        }
        int minCost = 0;
        for (int[] repair : edgesToRepair) {
            if (components == 1) {
                break;
            } else {
                if (union(repair[0], repair[1], root)) {
                    components--;
                    minCost += repair[2];
                }
            }
        }
        return components == 1 ? minCost : -1;
    }
    private boolean union(int a, int b, int[] root) {
        int rootA = find(a, root);
        int rootB = find(b, root);

        if (rootA == rootB) {
            return false;
        }

        if (rootA < rootB) {
            root[rootB] = rootA;
        } else {
            root[rootA] = rootB;
        }
        return true;
    }

    private int find(int node, int[] root) {
        if (node != root[node]) {
            root[node] = find(root[node], root);
        }
        return root[node];
    }

    public static void main(String[] args) {
        MinCostRepairEdge MST = new MinCostRepairEdge();
        int[][] edges = {{1,2},{2,3},{3,4},{4,5},{1,5}};
        int[][] edgesToRepair = {{1,2,12},{3,4,30},{1,5,8}};
        int cost = MST.repair(5, edges, edgesToRepair);
        System.out.println(cost);


        int[][] edges2 = {{1,2},{2,3},{4,5},{3,5},{1,6},{2,4}};
        int[][] edgesToRepair2 = {{1,6,410},{2,4,800}};
        cost = MST.repair(6, edges2, edgesToRepair2);
        System.out.println(cost);

        int[][] edges3 = {{1,2},{2,3},{4,5},{5,6},{1,5},{2,4},{3,4}};
        int[][] edgesToRepair3 = {{1,5,110},{2,4,84},{3,4,79}};
        cost = MST.repair(6, edges3, edgesToRepair3);
        System.out.println(cost);
    }
}
