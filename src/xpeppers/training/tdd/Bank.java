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

	public Money reduce(Expression sum) {
		double currentRate = 0;
		Money firstOperand = sum.aguend;
		while(sum.addend != null){
			sum = sum.addend;
			Money secondOperand = sum.aguend;
			String rateMap = secondOperand.currency() + " " + firstOperand.currency();
			currentRate = taxRateMap.get(rateMap);
			firstOperand = firstOperand.plus(secondOperand.times(currentRate));
		}
		return firstOperand;
	}
}
