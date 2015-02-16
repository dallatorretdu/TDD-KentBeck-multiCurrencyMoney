package xpeppers.training.tdd;

public class Dollar {
	protected int amount = 10;
	
	protected Dollar(int amount){
		this.amount = amount;
	}
	
	protected void times(int multiplier){
		amount *= multiplier;
	}
}
