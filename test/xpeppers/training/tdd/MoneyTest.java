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
	public void testMultipliationOnDollars() {
		Money five = Money.dollar(5);
		assertEquals(Money.dollar(10), five.times(2));
		assertEquals(Money.dollar(15), five.times(3));
	};
	
	@Test
	public void testEqualityOnMoney() {
		assertTrue(Money.dollar(5).equals( Money.dollar(5)));
		assertFalse(Money.dollar(5).equals( Money.dollar(12)));
		assertTrue(Money.franc(5).equals( Money.franc(5)));
		assertFalse(Money.franc(5).equals( Money.franc(12)));
	};
	
	@Test	
	public void testMultipliationOnFrancs() {
		Money five = Money.franc(5);
		assertEquals(Money.franc(10), five.times(2));
		assertEquals(Money.franc(15), five.times(3));
	};
	
	@Test
	public void testEqualityBetweenCurrenciesFails() {
		assertFalse(Money.dollar(5).equals( Money.franc(5)));
	};
	
	@Test
	public void testCurrencies() {
		assertEquals("USD", Money.dollar(1).currency());
		assertEquals("CHF", Money.franc(1).currency());
	};
	
	@Test
	public void testAddition() {
		assertEquals(Money.dollar(15), Money.dollar(5).plus(Money.dollar(10)));
		assertEquals(Money.franc(15), Money.franc(10).plus(Money.franc(5)));
	};
	
	//@Test
	public void testAdditionOnDifferentCurrencies() {
		Money dollars = Money.dollar(5);
		Money francs = Money.franc(10);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 0.5);
		bank.addRate("USD", "CHF", 2);
		Expression sumFrancsToDollars = new Expression(francs).addTo(dollars);
		Expression sumDollarsToFrancs = new Expression(dollars).addTo(francs);
		Money totalDollarsFrancs = bank.reduce(sumFrancsToDollars);
		Money totalFrancsDollars = bank.reduce(sumDollarsToFrancs);
		assertEquals(Money.dollar(10) , totalDollarsFrancs);
		assertEquals(Money.franc(20) , totalFrancsDollars);
	};
	
	@Test
	public void testExpressionStoresOperands() {
		Money dollars = Money.dollar(5);
		Money francs = Money.franc(10);
		Expression sum = new Expression(francs).addTo(dollars).addTo(dollars);
		assertTrue(sum.aguend.equals(dollars));
		assertTrue(sum.addend.aguend.equals(dollars));
		assertTrue(sum.addend.addend.aguend.equals(francs));
	}
	
	//@Test
	public void testAdditionOnTripleCurrencies() {
		Money dollars = Money.dollar(9);
		Money francs = Money.franc(22);
		Money pounds = Money.pound(4);
		Bank bank = bankRatesSetup();
		Expression sumFrancsToDollars = new Expression(francs).addTo(dollars);
		Money totalInDollars = bank.reduce(sumFrancsToDollars);
		assertEquals(Money.dollar(26.6) , totalInDollars);
		Expression sumDollarsToPounds = new Expression(totalInDollars).addTo(pounds);
		Money totalInPounds = bank.reduce(sumDollarsToPounds);
		assertEquals(Money.pound(10.65) , totalInPounds);
	};
	
	//@Test
	public void testConvertCurrencies() {
		Money dollars = Money.dollar(150);
		Bank bank = bankRatesSetup();
		Expression convertedFrancs = new Expression(dollars).convertTo("CHF");
		Money totalDollarsFrancs = bank.reduce(convertedFrancs);
		assertEquals(Money.franc(187.5) , totalDollarsFrancs);
	};
	
	@Test
	public void testChainedSums() {
		Money dollars = Money.dollar(50);
		Money dollarsFew = Money.dollar(20);
		Bank bank = bankRatesSetup();
		Expression sumDollars = new Expression(dollars).addTo(dollars).addTo(dollarsFew);
		Money total = bank.reduce(sumDollars);
		assertEquals(Money.dollar(120) , total);
	};
	
	@Test
	public void testMixedChainedSums() {
		Bank bank = bankRatesSetup();
		Expression sumDollars = new Expression(Money.dollar(50))
									.addTo(Money.dollar(70)).
									addTo(Money.franc(100))
									.addTo(Money.franc(250))
									.convertTo("GBP");
		Money total = bank.reduce(sumDollars);
		assertEquals(Money.pound(100) , total);
	};
	
	
	
}
