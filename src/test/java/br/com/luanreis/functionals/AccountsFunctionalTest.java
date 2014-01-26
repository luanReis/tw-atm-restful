package br.com.luanreis.functionals;

import br.com.luanreis.models.Account;
import br.com.luanreis.util.HibernateUtil;
import org.hibernate.Session;
import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountsFunctionalTest {

    private static Server server;

    @BeforeClass
    public static void startHsqldbServer() throws Exception {
        server = new Server();
        server.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        HibernateUtil.getSessionFactory().close();
        server.shutdown();
    }

    @Before
    public void setupData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Account account = new Account();
        account.setAccountNumber(1234L);
        account.setBalance(100);

        session.save(account);
        session.getTransaction().commit();
    }

    @Test
    public void shouldReturnTheStoredAccount() throws Exception {
        List<Account> accounts = HibernateUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from Account")
                .list();

        Account returnedAccount = accounts.get(0);

        assertEquals(1234L, returnedAccount.getAccountNumber());
    }
}
