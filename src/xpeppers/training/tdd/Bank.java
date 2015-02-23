package xpeppers.training.tdd;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	Map<String, Double> taxRateMap;
	
	public Bank(){
		taxRateMap = new HashMap<String, Double>();
	}

	public void addRate(String from, String to, double rate){
		taxRateMap.put(from+" "+to, rate);
	}

	public Money reduce(Expression expresion) {
		Money firstOperand = expresion.operand;
		while(expresion.recursive != null){
			expresion = expresion.recursive;
			firstOperand = executeOperation(expresion, firstOperand);
		}
		return firstOperand;
	}

	private Money executeOperation(Expression sum, Money firstOperand) {
		Money secondOperand = sum.operand;
		String rateMap = secondOperand.currency() + " " + firstOperand.currency();
		firstOperand = firstOperand.plus(secondOperand.times(taxRateMap.get(rateMap)));
		return firstOperand;
	}
}
