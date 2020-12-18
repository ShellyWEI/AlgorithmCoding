package LinkedIn.CA1;

/*
* Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes),
* write a function to check whether these edges make up a valid tree.
*
* n = 5, and edges = [[0,1], [0,2], [0,3], [1,4]
* */

public class ValidTree {
    // no loop and no separate union
    public boolean validTree(int n, int[][] edges) {
        int[] roots = new int[n];
        for (int i = 0; i < n; i++) {
            roots[i] = i;
        }
        for (int[] edge : edges) {
            if (!needUnion(edge[0], edge[1], roots)) {
                return false;
            }
        }
        // check if there is different components
        roots[0] = find(0, roots);
        for (int i = 1; i < n; i++) {
            if (find(i, roots) != roots[i - 1]) {
                return false;
            }
        }
        return true;
    }
    private boolean needUnion(int a, int b, int[] roots) {
        int parentA = find(a, roots);
        int parentB = find(b, roots);

        if (parentA == parentB) {
            return false;
        } else {
            if (parentA < parentB) {
                roots[parentB] = parentA;
            } else {
                roots[parentA] = parentB;
            }
            return true;
        }
    }
    private int find(int num, int[] roots) {
        if (roots[num] != num) {
            roots[num] = find(roots[num], roots);
        }
        return roots[num];
    }
}
