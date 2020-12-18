package LinkedIn.PhoneScreen;

import java.math.BigDecimal;
import java.util.*;
/*Follow up:
* - Add support for ! operator, use fact() to calculate.
* - Add support for >=, <= operator, which should push 0 and 1 to the stack.
* - Add support for '? if-then operator*/
public class ReversePolishNotation {
    public int reversePolishNotation(String[] input) throws Exception{
        Deque<Integer> deque = new LinkedList<>();
        for (String c : input) {
            try {
                deque.offerFirst(Integer.parseInt(c));
            } catch (NumberFormatException e){
                Integer sec;
                Integer fir;
                switch (c) {
                    case "+":
                        deque.offerFirst(deque.pollFirst() + deque.pollFirst());
                        break;
                    case "-": sec = deque.pollFirst();
                        fir = deque.pollFirst();
                        deque.offerFirst(fir - sec);
                        break;
                    case "*":
                        deque.offerFirst(deque.pollFirst() * deque.pollFirst());
                        break;
                    case "/":
                        sec = deque.pollFirst();
                        fir = deque.pollFirst();
                        deque.offerFirst( fir / sec);
                        break;
                    case "!":
                        int val = deque.pollFirst();
                        deque.offerFirst(fact(val).intValue());
                }
            }
        }
        if (deque.size() == 1) {
            return deque.pollFirst();
        }
        else {
            throw new Exception("Invalid input");
        }
    }

    private BigDecimal fact(int n) {
        if (n == 0 || n == 1) {
            return BigDecimal.valueOf(1);
        } else {
            return BigDecimal.valueOf(n).multiply(fact(n - 1));
        }
    }
}
