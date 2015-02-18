package xpeppers.training.tdd;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoneyTest {

	@Test	
	public void testMultipliationOnDollars() {
		Money five = Money.dollar(5);
		assertEquals(Money.dollar(10), five.times(2));
		assertEquals(Money.dollar(15), five.times(3));
	};
	
	@Test
	public void testEqualityOnDollars() {
		assertTrue(Money.dollar(5).equals( Money.dollar(5)));
		assertFalse(Money.dollar(5).equals( Money.dollar(12)));
	};
	
	@Test	
	public void testMultipliationOnFrancs() {
		Money five = Money.franc(5);
		assertEquals(Money.franc(10), five.times(2));
		assertEquals(Money.franc(15), five.times(3));
	};
	
	@Test
	public void testEqualityOnFrancs() {
		assertTrue(Money.dollar(5).equals( Money.dollar(5)));
		assertFalse(Money.dollar(5).equals( Money.dollar(12)));
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
	
}
