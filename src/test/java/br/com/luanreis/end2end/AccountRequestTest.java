package br.com.luanreis.end2end;

import br.com.luanreis.Main;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.junit.Assert.assertEquals;

public class AccountRequestTest {

    private static Main.WebServer webServer;

    @BeforeClass
    public static void startWebServer() throws Exception {
        webServer = new Main.WebServer();

        // start the server, but do NOT join the webserver thread
        webServer.start();
    }

    @AfterClass
    public static void stopWebServer() throws Exception {
        webServer.stop();
    }

    @Test
    public void shouldReturn404ForWrongPath() throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://localhost:8080/services/wrong-path");
        HttpResponse response = httpclient.execute(get);

        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldReturnAnEmptyArrayWhenNoAccountsWereStored() throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://localhost:8080/services/accounts");
        HttpResponse response = httpclient.execute(get);
        HttpEntity entity = response.getEntity();

        assertEquals(200, response.getStatusLine().getStatusCode());

        InputStream stream = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        assertEquals("[]", reader.readLine());
    }
}
