package br.com.luanreis.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.luanreis.exceptions.AccountManagementException;

@XmlRootElement
@Entity
public class Account {

	@Id
	private long id;
	private long number;
	private String holderName;
	private String holderCPF;
	private double balance;
	private AccountType type;
	private boolean shouldSave;

	@Transient
	private List<Transaction> transactions;

	public Account() {
		transactions = new ArrayList<Transaction>();
		shouldSave = true;
	}

	public long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getHolderCPF() {
		return holderCPF;
	}

	public void setHolderCPF(String holderCPF) {
		this.holderCPF = holderCPF;
	}

	public double getBalance() {
		return balance;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	protected Transaction getLastTransaction() {
		return transactions.get(transactions.size() - 1);
	}

	public void deposit(double amount) throws AccountManagementException {
		if (amount <= 0) {
			throw new AccountManagementException(
					"You must insert a value greater than zero.");
		} else {
			balance += amount;
			if (shouldSave)
				saveTransaction(new Transaction(amount, number,
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
				saveTransaction(new Transaction(amount, number,
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
			saveTransaction(new Transaction(amount,
					destinationAccount.getNumber(), TransactionType.TRANSFER));

		} catch (AccountManagementException e) {
			System.out.println(e.getMessage());
		}
	}

	private void saveTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

}