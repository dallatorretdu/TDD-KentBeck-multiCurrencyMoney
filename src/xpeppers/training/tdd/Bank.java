package xpeppers.training.tdd;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Double> taxRateMap;

    public Bank() {
        taxRateMap = new HashMap<>();
    }

    public void addRate(String from, String to, Double rate) {
        taxRateMap.put(from + " " + to, rate);
    }

    public Money reduce(Expression expression) {
        Money currentResult = expression.operand();
        while (thereAreMoreOperandsIn(expression)) {
            expression = expression.recursive();
            currentResult = executeOperation(expression, currentResult);
        }
        return currentResult;
    }

    private boolean thereAreMoreOperandsIn(Expression expression) {
        return expression.recursive() != null;
    }

    private Money executeOperation(Expression expression, Money money) {
        Money anotherMoney = expression.operand();
        anotherMoney = convert(anotherMoney, taxRateIdentifier(money, anotherMoney));
        return money.plus(anotherMoney);
    }

    private Money convert(Money money, String rateMapIdentifier) {
        return money.times(taxRateMap.get(rateMapIdentifier));
    }

    private String taxRateIdentifier(Money money, Money anotheMoney) {
        return anotheMoney.currency() + " " + money.currency();
    }

    public Money operand(MoneyTest moneyTest, Expression sum) {
        return sum.operand();
    }
}
