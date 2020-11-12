package Amazon.Onsite;

import java.util.Deque;
import java.util.LinkedList;

class ExpressionTokenizer {
    char[] expression;
    int index;
    ExpressionTokenizer(String expression) {
        this.index = 0;
        this.expression = expression.toCharArray();
    }
    public ExpressionToken nextToken() {
        while (index < expression.length && expression[index] == ' ') {
            index++;
        }
        if (index >= expression.length) {
            return null;
        }
        if (expression[index] == '(' || expression[index] == ')') {
            return new ExpressionToken(expression[index++], ExpressionToken.Type.PARENTHESIS);
        } else if (expression[index] == '+' || expression[index] == '-') {
            return new ExpressionToken(expression[index++], ExpressionToken.Type.OPERATOR);
        } else {
            int sum = 0;
            while (index < expression.length && Character.isDigit(expression[index])) {
                sum = sum * 10 + (expression[index++] - '0');
            }
            return new ExpressionToken(sum, ExpressionToken.Type.NUMBER);
        }
    }
}

class ExpressionToken {
    char token;
    int num;
    Type type;
    enum Type {
        NUMBER,
        OPERATOR,
        PARENTHESIS,
    }
    ExpressionToken(int num, Type type) {
        this.num = num;
        this.type = type;
    }
    ExpressionToken(char token, Type type) {
        this.token = token;
        this.type = type;
    }
    public Type getType() {
        return type;
    }
    // only when type is number
    public int intValue() {
        return num;
    }
    // only when type is not number
    public char charValue() {
        return token;
    }
}
public class BasicCalculator {
    class OperatorLevel {
        char operator;
        int level;
        OperatorLevel(char op, int level) {
            this.operator = op;
            this.level = level;
        }
    }
    public int calculate(String s) {
        Deque<OperatorLevel> operatorStack = new LinkedList<>();
        Deque<Integer> numberStack = new LinkedList<>();
        int curLevel = 0;
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(s);
        ExpressionToken next = tokenizer.nextToken();
        while (next != null) {
            switch (next.getType()) {
                case NUMBER:
                    numberStack.addFirst(next.intValue());
                    break;
                case OPERATOR:
                    while (!operatorStack.isEmpty() && operatorStack.peekFirst().level >= curLevel) {
                        calculateLevel(operatorStack, numberStack);
                    }
                    operatorStack.addFirst(new OperatorLevel(next.charValue(), curLevel));
                    break;
                case PARENTHESIS:
                    if (next.charValue() == '(') {
                        curLevel++;
                    } else {
                        curLevel--;
                    }
            }
            next = tokenizer.nextToken();
        }
        while (!operatorStack.isEmpty()) {
            calculateLevel(operatorStack, numberStack);
        }
        return numberStack.peekFirst();
    }
    private void calculateLevel(Deque<OperatorLevel> operator, Deque<Integer> num) {
            char op = operator.pollFirst().operator;
            int first = num.pollFirst();
            int sec = num.pollFirst();
            if (op == '+') {
                num.offerFirst(first + sec);
            } else if (op == '-') {
                num.offerFirst(sec - first);
            }
    }
    public static void main(String[] args) {
        BasicCalculator c = new BasicCalculator();
        c.calculate(" (1+(4+5+2)-3)+(6+8)");
    }

    // the one with + - * /
    public int calculateMulti(String s) {
        if (s == null) return 0;
        s = s.trim().replaceAll(" +", "");
        int length = s.length();

        int res = 0;
        long preVal = 0; // initial preVal is 0
        char sign = '+'; // initial sign is +
        int i = 0;
        while (i < length) {
            long curVal = 0;
            while (i < length && (int)s.charAt(i) <= 57 && (int)s.charAt(i) >= 48) { // int
                curVal = curVal*10 + (s.charAt(i) - '0');
                i++;
            }
            if (sign == '+') {
                res += preVal;  // update res
                preVal = curVal;
            } else if (sign == '-') {
                res += preVal;  // update res
                preVal = -curVal;
            } else if (sign == '*') {
                preVal = preVal * curVal; // not update res, combine preVal & curVal and keep loop
            } else if (sign == '/') {
                preVal = preVal / curVal; // not update res, combine preVal & curVal and keep loop
            }
            if (i < length) { // getting new sign
                sign = s.charAt(i);
                i++;
            }
        }
        res += preVal;
        return res;
    }
}
