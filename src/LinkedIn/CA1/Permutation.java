package LinkedIn.CA1;

import java.util.*;

public class Permutation<T> {
    //
    Collection<List<T>> generate(Collection<T> values) {
        List<T> each = new ArrayList<>();
        Collection<List<T>> res = new ArrayList<>();
        DFSGeneratePermutationWithoutDuplicates(new ArrayList<>(values), 0, each, res);
        return res;
    }

    private void DFSGeneratePermutationWithoutDuplicates(List<T> input, int index, List<T> each, Collection<List<T>> res) {
        if (each.size() == input.size()) {
            res.add(each);
            //each = new ArrayList<>(); // each is referenced to be empty
            return;
        }
        for (int i = 0; i < input.size(); i++) {
            if (each.contains(input.get(i))) {
                continue;
            }
            each.add(input.get(i));
            DFSGeneratePermutationWithoutDuplicates(input, i + 1, each, res);
            each.remove(each.size() - 1);
        }
    }


}
