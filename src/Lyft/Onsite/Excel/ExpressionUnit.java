package Lyft.Onsite.Excel;

public class ExpressionUnit {
    int unitType; // 0-> operation; 1-> value; 2-> variabale
    int value;
    char operation;
    String variable;

    public ExpressionUnit(char operation) {
        this.unitType = 0;
        this.operation = operation;
    }

    public ExpressionUnit(int value) {
        this.unitType = 1;
        this.value = value;
    }

    public ExpressionUnit(String variable) {
        this.unitType = 2;
        this.variable = variable;
    }

    public boolean isVariable() {
        return this.unitType == 2;
    }

    public boolean isValue() {
        return this.unitType == 1;
    }

    public boolean isOperation() {
        return this.unitType == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ExpressionUnit)) {
            return false;
        }
        ExpressionUnit other = (ExpressionUnit) o;
        if (this.unitType != other.unitType) {
            return false;
        }

        switch (this.unitType) {
            case 0: return this.operation == other.operation;
            case 1: return this.value == other.value;
            case 2: return this.variable.equals(other.variable);
            default: return false;
        }
    }

    @Override
    public String toString() {
        switch (this.unitType) {
            case 0: return Character.toString(operation);
            case 1: return Integer.toString(value);
            case 2: return variable;
            default: return null;
        }
    }

    @Override
    public int hashCode() {
        switch (this.unitType) {
            case 0: return Character.toString(operation).hashCode();
            case 1: return Integer.valueOf(value).hashCode();
            case 2: return variable.hashCode();
            default: return 0;
        }
    }
}
