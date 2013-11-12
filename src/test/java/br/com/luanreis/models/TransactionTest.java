package br.com.luanreis.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TransactionTest {

	@Test
	public void shouldHaveDate() {
		Transaction transaction = new Transaction(0.0, 001,
				TransactionType.DEPOSIT);
		assertNotNull(transaction.getDate());
	}

	@Test
	public void equalsTransaction() {
		double amount = 100.0;
		TransactionType type = TransactionType.DEPOSIT;
		Transaction transaction = new Transaction(amount, 001, type);
		Transaction sameTransaction = new Transaction(amount, 001, type);
		assertEquals(transaction, sameTransaction);
	}

}
