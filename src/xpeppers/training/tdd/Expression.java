package xpeppers.training.tdd;

public class Expression {
    private Money operand;
    private Expression recursive;

    public Expression(Money aguend) {
        operand = aguend;
    }

    private Expression(Money aguend, Expression currentExpression) {
        operand = aguend;
        recursive = currentExpression;
    }

    public Money operand() {
        return operand;
    }

    public Expression recursive() {
        return recursive;
    }

    public Expression add(Money aguend) {
        nestExpressionInRecursive();
        operand = aguend;
        return this;
    }

    public Expression subtract(Money aguend) {
        nestExpressionInRecursive();
        operand = aguend.times(-1);
        return this;
    }

    public Expression convertTo(String desideredMoneyCode) {
        nestExpressionInRecursive();
        operand = new Money(0, desideredMoneyCode);
        return this;
    }

    private void nestExpressionInRecursive() {
        recursive = new Expression(operand, recursive);
    }

}

/*
 * SCRATCHPAD - La concatenazione va in coda o in testa? - La conversione va effettuata verso l'operando di destra o sinistra?
 */