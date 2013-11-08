package br.com.luanreis.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import br.com.luanreis.exceptions.AccountManagementException;

@XmlRootElement
public class Account {
	private long id;
	private String holderName;
	private String holderCPF;
	private double balance;
	private AccountType accountType;
	private boolean shouldSave;
	private List<Transaction> transactions;

	public Account(long id, String holderName, String holderCPF,
			AccountType accountType) {
		this.id = id;
		this.holderName = holderName;
		this.holderCPF = holderCPF;
		this.accountType = accountType;
		transactions = new ArrayList<Transaction>();
		shouldSave = true;
	}

	public long getId() {
		return id;
	}

	public String getHolderName() {
		return holderName;
	}

	public String getHolderCPF() {
		return holderCPF;
	}

	public double getBalance() {
		return balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void deposit(double amount) throws AccountManagementException {
		if (amount <= 0) {
			throw new AccountManagementException(
					"You must insert a value greater than zero.");
		} else {
			balance += amount;
			if (shouldSave)
				saveTransaction(new Transaction(amount, id,
						TransactionType.DEPOSIT));
		}
	}

	public void withdraw(double amount) throws AccountManagementException {
		if (amount > balance) {
			throw new AccountManagementException("Insufficient balance.");
		} else if (amount <= 0) {
			throw new AccountManagementException(
					"You must insert a value greater than zero.");
		} else {
			balance -= amount;
			if (shouldSave)
				saveTransaction(new Transaction(amount, id,
						TransactionType.WITHDRAW));
		}
	}

	public void transfer(Account destinationAccount, double amount)
			throws AccountManagementException {
		if (destinationAccount == null)
			throw new AccountManagementException(
					"The destination account doesn't exist.");
		try {
			shouldSave = false;
			this.withdraw(amount);
			destinationAccount.deposit(amount);
			shouldSave = true;
			saveTransaction(new Transaction(amount, destinationAccount.getId(),
					TransactionType.TRANSFER));

		} catch (AccountManagementException e) {
			System.out.println(e.getMessage());
		}
	}

	private void saveTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

}