package LeetCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoCompleteSystem {

    class SentenceLibrary implements Comparable<SentenceLibrary> {
        String sentence;
        int freq;
        SentenceLibrary(String sentence, int freq) {
            this.sentence = sentence;
            this.freq = freq;
        }

        @Override
        public int compareTo(SentenceLibrary another) {
            if (this.freq == another.freq) {
                return this.sentence.compareTo(another.sentence);
            }
            return another.freq - this.freq;
        }
    }

    class TrieNode {
        Map<Character, TrieNode> children;
        List<SentenceLibrary> list;
        boolean isFinished;
        TrieNode() {
            children = new HashMap<>();
            list = new ArrayList<>(3);
            this.isFinished = false;
        }
    }

    TrieNode root;
    TrieNode cur;
    Map<String, SentenceLibrary> map;
    StringBuilder stringBuilder;

    public AutoCompleteSystem(String[] sentences, int[] times) {
        map = new HashMap<>();
        root = new TrieNode();
        for (int i = 0; i < sentences.length; i++) {
            addSentence(sentences[i], times[i]);
        }
        stringBuilder = new StringBuilder();
        cur = root;
    }

    private void addSentence(String sentence, int freq) {
        SentenceLibrary sentenceFreq = map.get(sentence);
        if (sentenceFreq == null) {
            sentenceFreq = new SentenceLibrary(sentence, freq);
            map.put(sentence, sentenceFreq);
        } else {
            sentenceFreq.freq += freq;
        }
        TrieNode cur = root;
        for (char c : sentence.toCharArray()) {
            TrieNode next = cur.children.get(c);
            if (next == null) {
                next = new TrieNode();
                cur.children.put(c, next);
            }
            if (!next.list.contains(sentenceFreq)) {
                next.list.add(sentenceFreq);
            }
            Collections.sort(next.list);
            if (next.list.size() > 3) {
                next.list.remove(next.list.size() - 1);
            }
            cur = next;
        }
        cur.isFinished = true;
    }

    public List<String> input(char c) {
        List<String> res = new ArrayList<>(3);
        if (c == '#') {
            String sentence = stringBuilder.toString();
            addSentence(sentence, 1);
            generateRes(res);
            stringBuilder = new StringBuilder();
            cur = root;
        } else {
            stringBuilder.append(c);
            if (cur == null) {
                return res;
            }
            cur = cur.children.get(c);
            generateRes(res);
        }
        return res;
    }

    private void generateRes(List<String> res) {
        if (cur != null) {
            for (SentenceLibrary sentenceLibrary : cur.list) {
                res.add(sentenceLibrary.sentence);
            }
        }
    }

    public static void main(String[] args) {
        AutoCompleteSystem auto = new AutoCompleteSystem(
            new String[]{"i love you","island","iroman","i love leetcode"},
            new int[]{5, 3, 2, 2});
        auto.input('i');
        auto.input(' ');
        auto.input('a');
        auto.input('#');

        auto.input('i');
        auto.input(' ');
        auto.input('a');
        auto.input('#');

        auto.input('i');
        auto.input(' ');
        auto.input('a');
        auto.input('#');
    }
}
