package br.com.luanreis.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import br.com.luanreis.exceptions.AccountManagementException;
import br.com.luanreis.models.Account;
import br.com.luanreis.util.HibernateUtil;

@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {

	@GET
	public List<Account> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Account> result = session.createQuery("from Account").list();
		session.getTransaction().commit();
		session.close();
		return result;
	}

	private Account get(long accountNumber) {
		Account account = null;
		for (Account storedAccount : getAll()) {
			if (storedAccount.getAccountNumber() == accountNumber) {
				account = storedAccount;
				break;
			}
		}
		return account;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Account account) {
		if (account.getBalance() != 0) {
			throw new WebApplicationException(422);
		} else if (get(account.getAccountNumber()) != null) {
			throw new WebApplicationException(409);
		}

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(account);
		session.getTransaction().commit();
		session.close();

		return Response.status(Response.Status.CREATED).entity(account).build();
	}

	@Path("{accountNumber}/{amount}")
	@PUT
	public void deposit(@PathParam("accountNumber") long accountNumber,
			@PathParam("amount") double amount) {

		Account account = get(accountNumber);
		if (account != null) {
			try {
				account.deposit(amount);
				Session session = HibernateUtil.getSessionFactory()
						.openSession();

				session.beginTransaction();
				session.update(account);
				session.getTransaction().commit();
				session.close();
			} catch (AccountManagementException e) {
				throw new WebApplicationException(400);
			}
		}
	}

}