package LinkedIn.CA1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

interface wordFinder {
    void init (Set<String> words);
    // each character can only be used once
    Set<String> find (List<Character> characters);

}
public class WordFinder {
    Map<Integer, Integer> map = new HashMap<>();

}
