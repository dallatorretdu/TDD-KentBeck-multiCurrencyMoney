package xpeppers.training.tdd;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Double> taxRateMap;
	
	public Bank(){
		taxRateMap = new HashMap<String, Double>();
	}

	public void addRate(String from, String to, double rate){
		taxRateMap.put(from+" "+to, rate);
	}

	public Money reduce(Expression expresion) {
		Money currentResult = expresion.operand;
		while(thereAreMoreOperandsIn(expresion)){
			expresion = expresion.recursive;
			currentResult = executeOperation(expresion, currentResult);
		}
		return currentResult;
	}

	private boolean thereAreMoreOperandsIn(Expression expresion) {
		return expresion.recursive != null;
	}

	private Money executeOperation(Expression expression, Money firstOp) {
		Money secondOp = expression.operand;
		secondOp = convert(secondOp, taxRateIdentifier(firstOp, secondOp));
		firstOp = firstOp.plus(secondOp);
		return firstOp;
	}

	private Money convert(Money secondOperand, String rateMapIdentifier) {
		return secondOperand.times(taxRateMap.get(rateMapIdentifier));
	}

	private String taxRateIdentifier(Money firstOperand, Money secondOperand) {
		return secondOperand.currency() + " " + firstOperand.currency();
	}
}
