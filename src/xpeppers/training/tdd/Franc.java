package xpeppers.training.tdd;

public class Franc extends Money{
	
	protected Franc(int amount, String currency){
		this.amount = amount;
		this.currency = currency;
	}
	
	protected Money times(int multiplier){
		return franc(amount * multiplier);
	}
}
