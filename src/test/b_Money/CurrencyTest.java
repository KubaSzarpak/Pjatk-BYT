package test.b_Money;

import static org.junit.Assert.*;

import b_Money.Currency;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.01);
		assertEquals(0.20, DKK.getRate(), 0.01);
		assertEquals(1.5, EUR.getRate(), 0.01);
	}
	
	@Test
	public void testSetRate() {
		double testRate = 0.5;
		SEK.setRate(testRate);
		DKK.setRate(testRate);
		EUR.setRate(testRate);

		assertEquals(testRate, SEK.getRate(), 0.01);
		assertEquals(testRate, DKK.getRate(), 0.01);
		assertEquals(testRate, EUR.getRate(), 0.01);
	}
	
	@Test
	public void testGlobalValue() {
		int amount = 10000;

		assertEquals(1500, SEK.universalValue(amount));
		assertEquals(2000, DKK.universalValue(amount));
		assertEquals(15000, EUR.universalValue(amount));
	}
	
	@Test
	public void testValueInThisCurrency() {
		int amount = 100;

		assertEquals(75, DKK.valueInThisCurrency(amount, SEK));
		assertEquals(10, EUR.valueInThisCurrency(amount, SEK));

		assertEquals(133, SEK.valueInThisCurrency(amount, DKK));
		assertEquals(13, EUR.valueInThisCurrency(amount, DKK));

		assertEquals(1000, SEK.valueInThisCurrency(amount, EUR));
		assertEquals(750, DKK.valueInThisCurrency(amount, EUR));


	}

}
