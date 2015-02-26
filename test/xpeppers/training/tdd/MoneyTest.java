package xpeppers.training.tdd;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoneyTest {

    public Bank bankRatesSetup(){
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 0.8);
        bank.addRate("CHF", "GBP", 0.2);
        bank.addRate("USD", "CHF", 1.25);
        bank.addRate("USD", "GBP", 0.25);
        bank.addRate("GBP", "USD", 4);
        bank.addRate("GBP", "CHF", 5);

        bank.addRate("USD", "USD", 1);
        bank.addRate("GBP", "GBP", 1);
        bank.addRate("CHF", "CHF", 1);
        return bank;
    }

    @Test	
    public void testMultipliationOnMoneyCalculatesCorrectly() {
        assertEquals(Money.dollar(10), Money.dollar(5).times(2));
        assertEquals(Money.pound(15), Money.pound(5).times(3));
    };

    @Test
    public void testEqualityOnMoneyOfSameAndMixedTipes() {
        assertTrue(Money.dollar(5).equals( Money.dollar(5)));
        assertFalse(Money.dollar(5).equals( Money.dollar(12)));
        assertTrue(Money.franc(5).equals( Money.franc(5)));
        assertFalse(Money.franc(5).equals( Money.franc(12)));
        assertTrue(Money.pound(5).equals( Money.pound(5)));
        assertFalse(Money.pound(5).equals( Money.pound(12)));

        assertFalse(Money.dollar(5).equals( Money.pound(5)));
    };

    @Test
    public void testCurrencies() {
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
        assertEquals("GBP", Money.pound(1).currency());
    };

    @Test
    public void testAdditionOnMoneyClass() {
        assertEquals(Money.dollar(15), Money.dollar(5).plus(Money.dollar(10)));
        assertEquals(Money.franc(15), Money.franc(10).plus(Money.franc(5)));
        assertEquals(Money.pound(15), Money.pound(10).plus(Money.pound(5)));
    };

    @Test
    public void testBankAdditionOnDifferentCurrencies() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 0.5);
        bank.addRate("USD", "CHF", 2);
        Expression sumFrancsToDollars = new Expression(Money.franc(10))
        .add(Money.dollar(5));
        Expression sumDollarsToFrancs = new Expression(Money.dollar(5))
        .add(Money.franc(10));
        assertEquals(Money.dollar(10) , bank.reduce(sumFrancsToDollars));
        assertEquals(Money.franc(20) , bank.reduce(sumDollarsToFrancs));
    };

    @Test
    public void testBankAdditionOnTripleCurrencies() {
        Bank bank = bankRatesSetup();

        Expression sumFrancsToDollars = new Expression(Money.franc(22)).add(Money.dollar(9));
        Money totalInDollars = bank.reduce(sumFrancsToDollars);
        assertEquals(Money.dollar(26.6) , totalInDollars);

        Expression sumDollarsToPounds = new Expression(totalInDollars).add(Money.pound(4));
        Money totalInPounds = bank.reduce(sumDollarsToPounds);
        assertEquals(Money.pound(10.65) , totalInPounds);
    };

    @Test
    public void testExpressionCanConvertCurrencies() {
        Bank bank = bankRatesSetup();
        Expression convertToFrancs = new Expression(Money.dollar(150)).convertTo("CHF");
        assertEquals(Money.franc(187.5) , bank.reduce(convertToFrancs));
    };

    @Test
    public void testExpressionStoresOperandsRecursively() {
        Money dollars = Money.dollar(5);
        Money francs = Money.franc(10);
        Expression sum = new Expression(francs).add(dollars).add(dollars);
        assertTrue(sum.operand.equals(dollars));
        assertTrue(sum.recursive.operand.equals(dollars));
        assertTrue(sum.recursive.recursive.operand.equals(francs));
    }

    @Test
    public void testBankChainedExpressionSumsOnDollars() {
        Bank bank = bankRatesSetup();
        Expression sumDollars = new Expression(Money.dollar(50))
        .add(Money.dollar(50))
        .add(Money.dollar(20));
        Money total = bank.reduce(sumDollars);
        assertEquals(Money.dollar(120) , total);
    };

    @Test
    public void testBankChainedChainedSumsWithConversions() {
        Bank bank = bankRatesSetup();
        Expression sumDollars = new Expression(Money.dollar(50))
        .add(Money.dollar(70))
        .add(Money.franc(100))
        .add(Money.franc(250))
        .convertTo("GBP");
        Money total = bank.reduce(sumDollars);
        assertEquals(Money.pound(100) , total);
    };

    @Test
    public void testBankExpressionCanHandleSubtractions() {
        Bank bank = bankRatesSetup();
        Expression sumDollars = new Expression(Money.dollar(60))
        .subtract(Money.dollar(50));

        Money total = bank.reduce(sumDollars);
        assertEquals(Money.dollar(10) , total);
    };


}
