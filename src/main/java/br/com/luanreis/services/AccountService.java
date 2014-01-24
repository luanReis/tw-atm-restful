package br.com.luanreis.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import br.com.luanreis.models.Account;
import br.com.luanreis.util.HibernateUtil;

@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {
	@GET
	public List<Account> get() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Account> result = session.createQuery("from Account").list();
		session.getTransaction().commit();
		session.close();
		return result;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Account account) {
		if (account.getBalance() != 0) {
			throw new WebApplicationException(422);
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(account);
		session.getTransaction().commit();
		session.close();

		return Response.status(Response.Status.CREATED).entity(account).build();
	}

}