package Laioffer;

import java.util.*;

public class FactorCombination {
    public static void main(String[] args) {
        FactorCombination t = new FactorCombination();
        t.combinations(24);
    }
    public List<List<Integer>> combinations(int target) {
        // Write your solution here
        List<Integer> factors = getFac(target);
        List<List<Integer>> comb = new ArrayList<>();
        DFS(factors, 0, target, new ArrayList<>(), comb);
        return comb;
    }
    private void DFS(List<Integer> fac, int index, int target, List<Integer> cur, List<List<Integer>> comb) {
        if (target == 1) {
            comb.add(new ArrayList<>(cur));
            return;
        }
        if (index == fac.size()) {
            return;
        }
        DFS(fac, index + 1, target, cur, comb);
        int curFactor = fac.get(index);
        int count = 0;
        while (target % curFactor == 0) {
            target = target / curFactor;
            count++;
            cur.add(curFactor);
            DFS(fac, index + 1, target, cur, comb);
        }
        for (int i = 0; i < count; i++) {
            cur.remove(cur.size() - 1);
        }
    }
    private List<Integer> getFac(int target) {
        List<Integer> res = new ArrayList<>();
        for (int i = target / 2; i >= 2; i--) {
            if (target % i == 0) {
                res.add(i);
            }
        }
        return res;
    }
}
