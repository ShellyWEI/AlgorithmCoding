package LinkedIn.CA1;

import java.util.HashMap;
import java.util.Map;

public class IsomorphicWords {
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        } else {
            Map<Character, Character> s2t = new HashMap<>();
            Map<Character, Character> t2s = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                Character mappedt = s2t.get(s.charAt(i));
                if (mappedt == null) {
                    // a not mapped to b yet,
                    // b previously has been mapped to other, return false,
                    // because if mapped to a before, a should have records
                    if (t2s.containsKey(t.charAt(i))) {
                        return false;
                    }
                    s2t.put(s.charAt(i), t.charAt(i));
                    t2s.put(t.charAt(i), s.charAt(i));
                } else {
                    if (mappedt != t.charAt(i)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
