package Amazon.Onsite;

import java.util.*;

public class AlienDictionary {
    // solution1 : 2d adj matrix and dfs
    public String alienOrder(String[] words) {
        int[] visited = new int[26];
        int[][] adjGraph = new int[26][26];
        StringBuilder sb = new StringBuilder();

        buildGraph(visited, adjGraph, words);

        for (int i = 0; i < 26; i++) {
            if (visited[i] == 0) {
                sb.append((char) ('a' + i));
                if (!DFSfindPath(visited, adjGraph, sb, i)) {
                    return "";
                }
            }
        }
        return sb.reverse().toString();
    }
    private boolean DFSfindPath (int[] visited, int[][] adj, StringBuilder sb, int i) {
        visited[i] = 1; // visiting
        for (int j = 0; j < 26; j++) {
            if (adj[i][j] == 1) {
                // That visiting node appears means loop.
                if (visited[j] == 1) {
                    return false;
                }
                if (visited[j] == 0) {
                    if (!DFSfindPath(visited, adj, sb, j)) {
                        return false;
                    }
                }
            }
        }
        visited[i] = 2; // visited
        sb.append((char) ('a' + i));
        return true;
    }
    private void buildGraph(int[] visited, int[][] adj, String[] words) {
        Arrays.fill(visited, -1); // non-exist char
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (char c : word.toCharArray()) {
                visited[c - 'a'] = 0;
            }
            if (i > 0) {
                String word1 = words[i - 1];

                for (int j = 0; j < Math.min(word1.length(), word.length()); j++) {
                    char c1 = word1.charAt(j);
                    char c2 = word.charAt(j);
                    if (c1 != c2) {
                        adj[c1 - 'a'][c2 - 'a'] = 1;
                        break;
                    } else {
                        visited[c2 - 'a'] = 0; // exited and non-visiting
                    }
                }
            }

        }
    }
}
