package br.com.luanreis.services;

import br.com.luanreis.models.Account;
import br.com.luanreis.models.AccountType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {

    @GET
    public Account get() {
        return new Account(001, "Luan", "000.000.000-00",
				AccountType.CURRENT_ACCOUNT);
    }

}