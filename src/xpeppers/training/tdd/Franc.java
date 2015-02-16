package xpeppers.training.tdd;

public class Franc extends Money{
	
	protected Franc(int amount){
		this.amount = amount;
	}
	
	protected Money times(int multiplier){
		return new Franc(amount * multiplier);
	}
}
