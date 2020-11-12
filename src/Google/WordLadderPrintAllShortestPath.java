package Google;

import java.util.*;

public class WordLadderPrintAllShortestPath {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // preprocess to generate all predecessors for all words
        Map<String, List<String>> predecessors = new LinkedHashMap<>();
        for (String s : wordList) {
            predecessors.put(s, new ArrayList<>());
        }
        // record the step generating from beginWord
        Map<String, Integer> step = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> set = new HashSet<>(wordList);
        queue.offer(beginWord);
        set.remove(beginWord);
        step.put(beginWord, 0);
        while (!queue.isEmpty()) {
            String parent = queue.poll();
            if (parent.equals(endWord)) {
                break;
            }
            for (String nei : findNeighbors(parent, wordList)) {
                // deduplicate
                if (set.remove(nei)) {
                    queue.offer(nei);
                }
                // only add predecessor when diff is 1
                int currentStepForThisNeighbor = step.get(parent) + 1;
                Integer prevStepForThisNeighbor = step.get(nei);
                if (prevStepForThisNeighbor == null || currentStepForThisNeighbor == prevStepForThisNeighbor) {
                    step.put(nei, currentStepForThisNeighbor);
                    predecessors.get(nei).add(parent);
                }
            }
        }
        // find all paths from endWord to beginWord
        List<List<String>> res = new ArrayList<>();
        List<String> curList = new ArrayList<>();
        curList.add(endWord);
        DFS(predecessors, endWord, beginWord, curList, res);
        return res;
    }
    private void DFS (Map<String, List<String>> predecessors, String curWord, String start, List<String> curList, List<List<String>> res) {
        if (curWord.equals(start)) {
            List<String> onePath = new ArrayList<>(curList);
            Collections.reverse(onePath);
            res.add(onePath);
            return;
        }
        List<String> preds = predecessors.get(curWord);
        if (preds != null) {
            for (String pred : predecessors.get(curWord)) {
                curList.add(pred);
                DFS(predecessors, pred, start, curList, res);
                curList.remove(curList.size() - 1);
            }
        }
    }
    private List<String> findNeighbors(String cur, List<String> words) {
        List<String> neis = new ArrayList<>();
        char[] word = cur.toCharArray();
        for (int i = 0; i < word.length; i++) {
            char orginal = word[i];
            for (int j = 0; j < 26; j++) {
                if (j != word[i] - 'a') {
                    word[i] = (char)(j + 'a');
                    String newWord = new String(word);
                    if (words.contains(newWord)) {
                        neis.add(newWord);
                    }
                }
            }
            word[i] = orginal;
        }
        return neis;
    }
    public static void main(String[] args) {
        List<String> input = Arrays.asList("git","hit","hog","hot","got");
        WordLadderPrintAllShortestPath t = new WordLadderPrintAllShortestPath();
        t.findLadders("git", "hot", input);
    }
}
