package xpeppers.training.tdd;

public class Dollar extends Money{
	
	protected Dollar(int amount, String currency){
		this.amount = amount;
		this.currency = currency;
	}
	
	protected Money times(int multiplier){
		return dollar(amount * multiplier);
	}
}
