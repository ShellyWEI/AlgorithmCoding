package LinkedIn.CA1;
/*
* Input: n = 5 and edges = [[0, 1], [1, 2], [3, 4]]
* */
public class ComponentsInUndirectedGraph {
    public int countComponents(int n, int[][] edges) {
        // make sets
        int[] roots = new int[n];
        int[] size = new int[n];
        for (int i = 0; i < n; i++) {
            roots[i] = i;
            size[i] = 1;
        }
        // union componets
        for (int[] edge : edges) {
            int rootA = find(edge[0], roots);
            int rootB = find(edge[1], roots);
            // need union
            if (rootA != rootB) {
                n--;
                if (size[rootA] < size[rootB]) {
                    size[rootB] += size[rootA];
                    roots[rootA] = rootB;
                } else {
                    size[rootA] += size[rootB];
                    roots[rootB] = rootA;
                }
            }
        }
        return n;
    }

    private int find(int node, int[] roots) {
        if (roots[node] != node) {
            roots[node] = find(roots[node], roots);
        }
        return roots[node];
    }
}
