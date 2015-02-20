package xpeppers.training.tdd;

public class Expression {
	protected Money aguend;
	protected Expression addend;
	
	public Expression(Money aguend){
		this.aguend = aguend;
	}
	public Expression(Money aguend, Expression addend){
		this.aguend = aguend;
		this.addend = addend;
	}
	
	public Expression addTo(Money aguend){
		this.addend = new Expression(this.aguend, this.addend);
		this.aguend = aguend;
		return this;
	}
	
	public Expression convertTo(String toValue){
		this.addend = new Expression(this.aguend, this.addend);
		this.aguend = new Money(0, toValue);
		return this;
	}
}
