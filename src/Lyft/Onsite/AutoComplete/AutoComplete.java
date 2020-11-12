package Lyft.Onsite.AutoComplete;

import java.io.*;
import java.util.*;

/**
 * Assumption:
 * no duplicate word in dictionary
 * word only contains lowercase letter
 * input: 已知单词个数的字典，单词出现的顺序是rank
 * App      3
 * Apple    5
 * Ban      2
 * Banana   2
 * Orange   1
 * Organize 2
 * */
public class AutoComplete {

    class WordRank implements Comparable<WordRank>{
        String word;
        int count;
        int rank;
        WordRank(String word, int count, int rank) {
            this.rank = rank;
            this.count = count;
            this.word = word;
        }

        @Override
        public int compareTo(WordRank another) {
            if (this.count == another.count) {
                return this.rank < another.rank ? -1 : 1;
            }
            return this.count > another.count ? -1 : 1;
        }
    }
    class TrieNode {
        TrieNode[] children;
        List<WordRank> wordsShareSamePrefix;
        TrieNode() {
            this.children = new TrieNode[26];
            this.wordsShareSamePrefix = new ArrayList<>();
        }
    }

    TrieNode root;
    int K;
    Map<String, WordRank> map;
    AutoComplete (Scanner in, int K) {
        this.K = K;
        this.map = new HashMap<>();
        this.root = buildTree(in);
    }

    private TrieNode buildTree(Scanner in) {
        int rank = 0;
        root = new TrieNode();
        while (in.hasNext()) {
            rank++;
            String[] wordCount = in.nextLine().split(" ");
            String word = wordCount[0];
            int count = Integer.parseInt(wordCount[1]);
            // duplicate word in dictionary, we don't process again
            if (map.containsKey(word)) {
                continue;
            }
            map.put(word, new WordRank(word, count, rank));

            // build trie for each character of word
            TrieNode prevNode = root;
            for (char c : word.toCharArray()) {
                TrieNode curNode = prevNode.children[c - 'a'];
                if (curNode == null) {
                    curNode = new TrieNode();
                    prevNode.children[c - 'a'] = curNode;
                }
                curNode.wordsShareSamePrefix.add(new WordRank(word, count, rank));
                prevNode = curNode;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        List<String> dictionary = new ArrayList<>();
        try {
            String cwd = System.getProperty("user.dir");
            //System.out.println(System.getProperty("user.dir"));
            Scanner dictionaryFile = new Scanner(new FileInputStream(cwd + "/src/Lyft/Onsite/autoCompleteSample.txt"));
            AutoComplete autoComplete = new AutoComplete(dictionaryFile, 5);
            Scanner input = new Scanner(System.in);
            while (input.hasNext()) {
                String prefix = input.nextLine();
                autoComplete.generateTopKWords(prefix);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateTopKWords(String prefix) {
        // find with node with dame prefix
        TrieNode cur = root;
        for (char c : prefix.toLowerCase().toCharArray()) {
            TrieNode next = cur.children[c - 'a'];
            if (next == null) {
                System.out.println("No word starting with such prefix in dictionary");
                return;
            }
            cur = next;
        }
        List<WordRank> result = cur.wordsShareSamePrefix;
        Collections.sort(result);
        for (int i = 0; i < Math.min(K, result.size()); i++) {
            WordRank wordRank = result.get(i);
            System.out.print(wordRank.word + " " + wordRank.count + " ");
        }
        System.out.println();
    }
}
