package xpeppers.training.tdd;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoneyTest {

	@Test	
	public void testMultipliationOnDollars() {
		Money five = Money.dollar(5);
		assertEquals(new Dollar(10), five.times(2));
		assertEquals(new Dollar(15), five.times(3));
	}
	
	@Test
	public void testEqualityOnDollars() {
		assertTrue(new Dollar(5).equals( new Dollar(5)));
		assertFalse(new Dollar(5).equals( new Dollar(12)));
	}
	
	@Test	
	public void testMultipliationOnFrancs() {
		Franc five = new Franc(5);
		assertEquals(new Franc(10), five.times(2));
		assertEquals(new Franc(15), five.times(3));
	}
	
	@Test
	public void testEqualityOnFrancs() {
		assertTrue(new Dollar(5).equals( new Dollar(5)));
		assertFalse(new Dollar(5).equals( new Dollar(12)));
	} 
	
	@Test
	public void testEqualityBetweenCurrenciesFails() {
		assertFalse(new Dollar(5).equals( new Franc(5)));
	}
}
