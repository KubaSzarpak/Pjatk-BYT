package test.b_Money;

import static org.junit.Assert.*;

import b_Money.*;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
		SweBank.deposit("Bob", new Money(10000000, SEK));
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		String accountId = "test1";
		SweBank.openAccount(accountId);

		// Account is not added to accountlist
		// accountlist.get(...) should be replaced with accountlist.put(...)
		assertEquals(0, SweBank.getBalance(accountId), 0.01);
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(100, SEK));
		assertEquals(100001.0, SweBank.getBalance("Bob"), 0.01);

		// if statement is incorrect. If statement should be negated
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.withdraw("Bob", new Money(10000000, SEK));

		assertEquals(0.0, SweBank.getBalance("Bob"), 0.01);

		//Withdraw uses deposit() method instead of withdraw()
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {

		assertEquals(0, SweBank.getBalance("Ulrika"), 0.01);
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		Money money = new Money(5000000, SEK);
		// transfer between banks
		SweBank.transfer("Bob", Nordea, "Bob", money);
		assertEquals(50000.0, SweBank.getBalance("Bob"), 0.01);
		assertEquals(50000.0, Nordea.getBalance("Bob"), 0.01);

		// transfer inside bank
		SweBank.transfer("Bob", "Ulrika", money);
		assertEquals(0.0, SweBank.getBalance("Bob"), 0.01);
		assertEquals(50000.0, SweBank.getBalance("Ulrika"), 0.01);

		// transfer inside bank has double fromaccount
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// it is redundant to test this methods if account.addTimedPayment() and account.removeTimedPayment() are tested.
		// There is no way to check if this methods worked correctly. It at least requires getAccount(accountid) method.

		String timedPaymentId = "test1";
		String fromAccountId = "Bob";
		String toAccountId = "Ulrika";
		Account fromAccount = SweBank.getAccount(fromAccountId);

		// Add time payment test
		SweBank.addTimedPayment(fromAccountId, timedPaymentId, 0, 100, new Money(0, SEK), SweBank, toAccountId);

		assertTrue("Should exists", fromAccount.timedPaymentExists(timedPaymentId));

		// Add time payment test
		SweBank.removeTimedPayment(fromAccountId, timedPaymentId);

		assertFalse("Should not exists", fromAccount.timedPaymentExists(timedPaymentId));
	}
}
