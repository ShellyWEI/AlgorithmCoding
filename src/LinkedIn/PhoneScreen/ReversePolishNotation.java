package LinkedIn.PhoneScreen;

import java.util.*;
/*Follow up:
* - Add support for ! operator, use fact() to calculate.
* - Add support for >=, <= operator, which should push 0 and 1 to the stack.
* - Add support for '? if-then operator*/
public class ReversePolishNotation {
    public int reversePolishNotation(String[] input) throws Exception{
        Deque<Integer> deque = new LinkedList<>();
        for (String c : input) {
            if (Character.isDigit(c.charAt(0))) {
                deque.offerFirst(Character.getNumericValue(c.charAt(0)));
            } else {
                int res;
                Integer sec;
                Integer fir;
                try {
                    switch (c) {
                        case "+":
                            res = deque.pollFirst() + deque.pollFirst();
                            break;
                        case "-":
                            sec = deque.pollFirst();
                            fir = deque.pollFirst();
                            res = fir - sec;
                            break;
                        case "*":
                            res = deque.pollFirst() * deque.pollFirst();
                            break;
                        case "/":
                            sec = deque.pollFirst();
                            fir = deque.pollFirst();
                            res = fir / sec;
                            break;
                        default:
                            throw new NoSuchElementException();
                    }
                } catch (NullPointerException e) {
                    throw new NullPointerException("deque is null");
                }
                deque.offerFirst(res);
            }
        }
        if (deque.size() == 1) {
            return deque.pollFirst();
        }
        else {
            throw new Exception("Invalid input");
        }
    }
}
