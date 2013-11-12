package br.com.luanreis.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.luanreis.exceptions.AccountManagementException;

public class AccountStatementTest {
	private Account account;

	@Before
	public void setUp() {
		account = new Account();
		account.setId(001);
		account.setHolderName("Luan");
		account.setHolderCPF("000.000.000-00");
		account.setAccountType(AccountType.CURRENT_ACCOUNT);
	}

	@Test
	public void lastTransaction() throws AccountManagementException {
		account.deposit(200.0);
		Transaction depositTransaction = new Transaction(200.0,
				account.getId(), TransactionType.DEPOSIT);
		assertEquals(depositTransaction,
				new AccountStatement(account).getLastTransaction());

		account.withdraw(100.0);
		Transaction withdrawTransaction = new Transaction(100.0,
				account.getId(), TransactionType.WITHDRAW);
		assertEquals(withdrawTransaction,
				new AccountStatement(account).getLastTransaction());

		Account destinationAccount = new Account();
		destinationAccount.setId(002);
		destinationAccount.setHolderName("Rodrigo");
		destinationAccount.setHolderCPF("111.111.111-11");
		destinationAccount.setAccountType(AccountType.SAVINGS_ACCOUNT);
		account.transfer(destinationAccount, 100.0);
		Transaction transferTransaction = new Transaction(100.0,
				destinationAccount.getId(), TransactionType.TRANSFER);
		assertEquals(transferTransaction,
				new AccountStatement(account).getLastTransaction());

	}
}
