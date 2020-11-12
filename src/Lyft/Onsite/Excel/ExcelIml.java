package Lyft.Onsite.Excel;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

public class ExcelIml {
    Map<ExpressionUnit, Integer> unitToCurrentValue;
    Map<ExpressionUnit, Set<ExpressionUnit>> unitToDirectDependencies;

    ExcelIml() {
        this.unitToCurrentValue = new HashMap<>();
        this.unitToDirectDependencies = new HashMap<>();
    }

    public void parse(Scanner in, PrintWriter out) {
        while (in.hasNext()) {
            String newLine = in.nextLine().trim();
            if (newLine.equals("END")) {
                break;
            }

            ExpressionTokens expTokens = ExpressionTokens.parseFromString(newLine, out);
            if (expTokens != null) {
                List<ExpressionUnit> expression = expTokens.getExpressionList();
                // make sure no circular dependency
                boolean goodDependency = true;
                for (ExpressionUnit expressionUnit : expression) {
                    if (expressionUnit.isVariable()) {
                        if (!checkDependency(expTokens.getTarget(), expressionUnit)) {
                            goodDependency = false;
                            break;
                        }
                    }
                }
                if (!goodDependency) {
                    out.print("Error: bad dependency\n");
                    out.flush();
                } else {
                    // calculate the result!
                    Integer result = expTokens.calculate(unitToCurrentValue, out);
                    if (result != null) {
                        unitToCurrentValue.put(expTokens.getTarget(), result);
                        out.print("Result for token " + expTokens.getTarget().toString() + ": " + result + "\n");
                        out.flush();

                        // update the dependency tree if it's a valid expression
                        updateDependency(expTokens.getTarget(), expression);
                    }
                }
            }
        }
    }

    private boolean checkDependency(ExpressionUnit target, ExpressionUnit dependant) {
        // make sure dependant doesn't also depend on target
        boolean noCircularDependency = true;
        Set<ExpressionUnit> visited = new HashSet<>();
        noCircularDependency = dfs(dependant, this.unitToDirectDependencies, visited, target);

        return noCircularDependency;
    }

    private static boolean dfs(ExpressionUnit current, Map<ExpressionUnit, Set<ExpressionUnit>> route, Set<ExpressionUnit> visited, ExpressionUnit devil) {
        if (visited.contains(current)) {
            return true;
        }
        visited.add(current);
        Set<ExpressionUnit> dependencies = route.get(current);
        if (dependencies != null) {
            for (ExpressionUnit dependant : dependencies) {
                if (dependant.equals(devil)) {
                    return false;
                }
                boolean dependantNotDependsOnDevil = dfs(dependant, route, visited, devil);
                if (!dependantNotDependsOnDevil) {
                    return false;
                }
            }
        }
        // good other wise
        return true;
    }

    private void updateDependency(ExpressionUnit unit, List<ExpressionUnit> newDependencies) {
        Set<ExpressionUnit> currentDependencies = this.unitToDirectDependencies.get(unit);
        if (currentDependencies == null) {
            currentDependencies = new HashSet<>();
            this.unitToDirectDependencies.put(unit, currentDependencies);
        }
        for (ExpressionUnit newDependency : newDependencies) {
            if (newDependency.isVariable()) {
                currentDependencies.add(newDependency);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // read from standard input
        Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        // write to standard output
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"), true);

        ExcelIml excelImlp = new ExcelIml();
        excelImlp.parse(in, out);
    }
}
