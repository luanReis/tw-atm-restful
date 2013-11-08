package br.com.luanreis.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Transaction {
	private Date date;
	private double amount;
	private long accountId;
	private TransactionType type;

	public Transaction(double amount, long accountId, TransactionType type) {
		date = new Date();
		this.amount = amount;
		this.accountId = accountId;
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public long getAccountId() {
		return accountId;
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
				&& accountId == transaction.accountId
				&& (type == transaction.type || (type != null && type
						.equals(transaction.type)));
	}

	@Override
	public int hashCode() {
		int hash = 7;
		long bits = Double.doubleToLongBits(amount);
		hash = 31 * hash + (int) (bits ^ (bits >>> 32));
		hash = 31 * hash + (int) (accountId ^ (accountId >>> 32));
		hash = 31 * hash + (type == null ? 0 : type.hashCode());
		return hash;
	}

}
