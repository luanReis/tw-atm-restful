package br.com.luanreis.models;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
	private Date date;
	private double amount;
	private long accountNumber;
	private TransactionType type;

	public Transaction(double amount, long accountNumber, TransactionType type) {
		date = new Date();
		this.amount = amount;
		this.accountNumber = accountNumber;
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public TransactionType getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		Transaction transaction = (Transaction) obj;
		return amount == transaction.amount
				&& accountNumber == transaction.accountNumber
				&& (type == transaction.type || (type != null && type
						.equals(transaction.type)));
	}

	@Override
	public int hashCode() {
		int hash = 7;
		long bits = Double.doubleToLongBits(amount);
		hash = 31 * hash + (int) (bits ^ (bits >>> 32));
		hash = 31 * hash + (int) (accountNumber ^ (accountNumber >>> 32));
		hash = 31 * hash + (type == null ? 0 : type.hashCode());
		return hash;
	}

}
