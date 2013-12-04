package br.com.luanreis.services;

import br.com.luanreis.models.Account;
import br.com.luanreis.models.AccountType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.WebApplicationException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {
	private static List<Account> accounts;

	static {
		accounts = new ArrayList<Account>();
		Account a1 = new Account();
		Account a2 = new Account();

		a1.setId(001);
		a1.setHolderName("Luan");
		a1.setHolderCPF("000.000.000-00");
		a1.setAccountType(AccountType.CURRENT_ACCOUNT);

		a2.setId(002);
		a2.setHolderName("Rodrigo");
		a2.setHolderCPF("111.111.111-11");
		a2.setAccountType(AccountType.CURRENT_ACCOUNT);

		accounts.add(a1);
		accounts.add(a2);
	}

	@GET
	public List<Account> get() {
		
		SessionFactory sessionFactory = new Configuration()
				.configure()
				.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save( new Account() );
		session.getTransaction().commit();
		session.close();
		
		return accounts;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Account account) {
		if (account.getBalance() != 0) {
			throw new WebApplicationException(422);
		}
		accounts.add(account); 
		return Response.status(Response.Status.CREATED).entity(account).build();
	}

}