package xpeppers.training.tdd;


class Money {

    private double amount;
    private String currency;

    public Money(double amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }
        
    static Money dollar(double amount){
        return new Money(amount, "USD");
    }
    static Money franc(double amount){
        return new Money(amount, "CHF");
    }
    static Money pound(double amount){
        return new Money(amount, "GBP");
    }

    public Money times(double multiplier){
        return new Money(amount * multiplier, currency);
    }

    public Money plus(Money addend) {
        return new Money(this.amount + addend.amount, this.currency);
    }

    public String currency() {
        return currency;
    }

    public String toString() {
        return amount + " " + currency;
    }

    public boolean equals(Object object){
        Money money = (Money) object;
        return amount == money.amount
                && currency == money.currency;
    }


}
