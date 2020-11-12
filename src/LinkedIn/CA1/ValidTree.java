package LinkedIn.CA1;

/*
* Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes),
* write a function to check whether these edges make up a valid tree.
*
* n = 5, and edges = [[0,1], [0,2], [0,3], [1,4]
* */

import java.util.Arrays;

public class ValidTree {
    public boolean validGraghTree(int n, int[][] edges) {
        // make set
        int[] parents = new int[n];
        int[] size = new int[n];
        Arrays.fill(size, 1);
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        for (int[] edge : edges) {
            // before union two nodes and their parents are same
            // means it has circle
            if (!union(edge[0], edge[1], parents, size)) {
                return false;
            }
        }
        // check if no connected part
        // remember those last linked nodes if not someone else's parents,
        // update parents will only occurred in find, so should check all the nodes' parents again
        int root = -1;
        for (int i = 0; i < n; i++) {
            int rootOfi = find(i, parents);
            if (root == -1) {
                root = rootOfi;
            } else if (root != rootOfi) {
                return false;
            }
        }
        return true;
    }
    // return true if union operation is successful achieved;
    // false if no union need
    private boolean union(int x, int y, int[] parents, int[] size) {
        int parentX = find(x, parents);
        int parentY = find(y, parents);

        // if two nodes are on the same set
        if (parentX == parentY) {
            return false;
        }

        if (size[parentX] < size[parentY]) {
            size[parentY] += size[parentX];
            parents[parentX] = parentY;
        } else {
            size[parentX] += size[parentY];
            parents[parentY] = parentX;
        }
        return true;
    }
    private int find(int x, int[] parents) {
        if (parents[x] != x) {
            parents[x] = find(parents[x], parents);
        }
        return parents[x];
    }
}
