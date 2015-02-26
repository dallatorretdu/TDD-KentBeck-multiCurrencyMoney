package xpeppers.training.tdd;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Double> taxRateMap;

    public Bank(){
        taxRateMap = new HashMap<String, Double>();
    }

    public void addRate(String from, String to, double rate){
        taxRateMap.put(from + " " + to, rate);
    }

    public Money reduce(Expression expresion) {
        Money currentResult = expresion.operand();
        while(thereAreMoreOperandsIn(expresion)){
            expresion = expresion.recursive();
            currentResult = executeOperation(expresion, currentResult);
        }
        return currentResult;
    }

    private boolean thereAreMoreOperandsIn(Expression expresion) {
        return expresion.recursive() != null;
    }

    private Money executeOperation(Expression expression, Money money) {
        Money anotherMoney = expression.operand();
        anotherMoney = convert(anotherMoney, taxRateIdentifier(money, anotherMoney));
        money = money.plus(anotherMoney);
        return money;
    }

    private Money convert(Money money, String rateMapIdentifier) {
        return money.times(taxRateMap.get(rateMapIdentifier));
    }

    private String taxRateIdentifier(Money money, Money anotherMoney) {
        return anotherMoney.currency() + " " + money.currency();
    }
    
    public Money operand(MoneyTest moneyTest, Expression sum) {
        return sum.operand();
    }
}
