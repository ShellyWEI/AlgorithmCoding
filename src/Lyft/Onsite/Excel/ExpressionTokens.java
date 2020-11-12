package Lyft.Onsite.Excel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressionTokens {
    private final ExpressionUnit targetVariable;
    private final List<ExpressionUnit> expressionUnitList;

    // the constructor is made private on purpose so that people have to use the parseFromString function
    private ExpressionTokens(ExpressionUnit targetVariable, List<ExpressionUnit> expressionUnitList) {
        this.targetVariable = targetVariable;
        this.expressionUnitList = expressionUnitList;
    }

    public List<ExpressionUnit> getExpressionList() {
        return this.expressionUnitList;
    }

    public ExpressionUnit getTarget() {
        return this.targetVariable;
    }

    public Integer calculate(Map<ExpressionUnit, Integer> unitToCurrentValue, PrintWriter out) {
        int result = 0;
        ExpressionUnit currentOperation = null;
        for (ExpressionUnit expressionUnit : expressionUnitList) {
            if (expressionUnit.isOperation()) {
                currentOperation = expressionUnit;
            } else {
                // either variable or pure value
                int value;
                if (expressionUnit.isVariable()) {
                    Integer v = unitToCurrentValue.get(expressionUnit);
                    if (v == null) {
                        out.print("Error: " + unitToCurrentValue.toString() + " doesn't have a value");
                        out.flush();
                        return null;
                    }
                    value = v;
                } else {
                    value = expressionUnit.value;
                }

                if (currentOperation == null) {
                    // first time getting a value
                    result = value;
                } else {
                    // calculate
                    if (currentOperation.operation == '+') {
                        result += value;
                    } else if (currentOperation.operation == '-') {
                        result -= value;
                    } else {
                        out.println("Error: unknown operation " + currentOperation.toString());
                        out.flush();
                        return null;
                    }
                    currentOperation = null;
                }
            }
        }

        return new Integer(result);
    }

    public static ExpressionTokens parseFromString(String newLine, PrintWriter out) {
        if (newLine == null) {
            out.print("Null input\n");
            out.flush();
            return null;
        }

        // trim and put everything to lower case: put a2 (a1 + 3)
        newLine = newLine.trim().toLowerCase();
        // first, get rid of the "put" header
        int index = newLine.indexOf("put");
        newLine = newLine.substring(index + 3).trim();

        // get the target variable and remove it from the expression
        index = newLine.indexOf("(");
        ExpressionUnit targetVariable = new ExpressionUnit(newLine.substring(0, index).trim());
        // get rid of the parentheses ()
        if (!newLine.endsWith(")")) {
            out.print("Input expression is not end with )\n");
            out.flush();
            return null;
        }
        newLine = newLine.substring(index + 1, newLine.length() - 1).trim();

        // parse the expression
        List<ExpressionUnit> expressionUnitList = new ArrayList<>();
        char[] chars = newLine.toCharArray();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (isOperation(c)) {
                // add previous token to list first
                if (currentToken.length() > 0) {
                    String newToken = currentToken.toString().trim();

                    addTokenToList(expressionUnitList, newToken);

                    // clear the previous token
                    currentToken.setLength(0);
                }
                expressionUnitList.add(new ExpressionUnit(c));
            } else {
                // add to the current token
                currentToken.append(c);
            }
        }

        // remember to add the last token to the list
        if (currentToken.length() > 0) {
            String lastToken = currentToken.toString().trim();
            addTokenToList(expressionUnitList, lastToken);
        }

        // check whether the target is depending on itself
        for (ExpressionUnit unit : expressionUnitList) {
            if (targetVariable.equals(unit)) {
                out.print("Error: " + targetVariable.toString() + " depends on it's own value");
                out.flush();
                return null;
            }
        }
        return new ExpressionTokens(targetVariable, expressionUnitList);
    }

    private static void addTokenToList(List<ExpressionUnit> expressionUnitList, String newToken) {
        // check whether it's purely integer
        try {
            int value = Integer.parseInt(newToken);
            expressionUnitList.add(new ExpressionUnit(value));
        } catch (NumberFormatException e) {
            // this is a variable
            expressionUnitList.add(new ExpressionUnit(newToken));
        }
    }
    private static boolean isAlphabet(char c) {
        if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOperation(char c) {
        if (c == '+' || c == '-') {
            return true;
        } else {
            return false;
        }
    }
}
