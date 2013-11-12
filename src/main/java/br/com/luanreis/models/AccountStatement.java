package br.com.luanreis.models;

import java.util.List;

public class AccountStatement {
	private Account account;

	public AccountStatement(Account account) {
		this.account = account;
	}

	public Transaction getLastTransaction() {
		List<Transaction> transactions = account.getTransactions();
		return transactions.get(transactions.size() - 1);
	}
}
