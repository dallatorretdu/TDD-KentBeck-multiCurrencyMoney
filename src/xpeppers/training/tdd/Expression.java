package xpeppers.training.tdd;

public class Expression {
	protected Money operand;
	protected Expression recursive;
	
	public Expression(Money aguend){
		this.operand = aguend;
	}
	public Expression(Money aguend, Expression currentExpression){
		this.operand = aguend;
		this.recursive = currentExpression;
	}
	
	public Expression add(Money aguend){
		this.recursive = new Expression(this.operand, this.recursive);
		this.operand = aguend;
		return this;
	}
	
	public Expression subtract(Money aguend){
		this.recursive = new Expression(this.operand, this.recursive);
		this.operand = aguend.times(-1);
		return this;
	}
	
	public Expression convertTo(String resultvalueCode){
		this.recursive = new Expression(this.operand, this.recursive);
		this.operand = new Money(0, resultvalueCode);
		return this;
	}
}
