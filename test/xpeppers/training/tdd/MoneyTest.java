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
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 0.5);
		bank.addRate("USD", "CHF", 2);
		Expression sumFrancsToDollars = new Expression(Money.franc(10)).add(Money.dollar(5));
		Expression sumDollarsToFrancs = new Expression(Money.dollar(5)).add(Money.franc(10));
		Money totalsumFrancsToDollars = bank.reduce(sumFrancsToDollars);
		Money totalsumDollarsToFrancs = bank.reduce(sumDollarsToFrancs);
		assertEquals(Money.dollar(10) , totalsumFrancsToDollars);
		assertEquals(Money.franc(20) , totalsumDollarsToFrancs);
	};
	
	@Test
	public void testExpressionStoresOperands() {
		Money dollars = Money.dollar(5);
		Money francs = Money.franc(10);
		Expression sum = new Expression(francs).add(dollars).add(dollars);
		assertTrue(sum.operand.equals(dollars));
		assertTrue(sum.recursive.operand.equals(dollars));
		assertTrue(sum.recursive.recursive.operand.equals(francs));
	}
	
	//@Test
	public void testAdditionOnTripleCurrencies() {
		Bank bank = bankRatesSetup();
		
		Expression sumFrancsToDollars = new Expression(Money.franc(22)).add(Money.dollar(9));
		Money totalInDollars = bank.reduce(sumFrancsToDollars);
		assertEquals(Money.dollar(26.6) , totalInDollars);
		
		Expression sumDollarsToPounds = new Expression(totalInDollars).add(Money.pound(4));
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
		Bank bank = bankRatesSetup();
		Expression sumDollars = new Expression(Money.dollar(50))
									.add(Money.dollar(50))
									.add(Money.dollar(20));
		Money total = bank.reduce(sumDollars);
		assertEquals(Money.dollar(120) , total);
	};
	
	@Test
	public void testMixedChainedSums() {
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
	public void testMixedSubtractions() {
		Bank bank = bankRatesSetup();
		Expression sumDollars = new Expression(Money.dollar(60))
									.subtract(Money.dollar(50));
									
		Money total = bank.reduce(sumDollars);
		assertEquals(Money.dollar(10) , total);
	};
	
	
}
