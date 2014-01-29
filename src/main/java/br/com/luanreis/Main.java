package br.com.luanreis;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

    public static void main(String[] args) throws Exception {

        // start the webserver
        //
        // when joinning the webserver thread, the program
        // blocks here waiting forever
        new WebServer().start().join();
    }

    public static class WebServer {

        private Server server;

        public WebServer start() throws Exception {
            String webappDirLocation = "src/main/webapp/";

            String webPort = System.getenv("PORT");
            if (webPort == null || webPort.isEmpty()) {
                webPort = "8080";
            }

            server = new Server(Integer.valueOf(webPort));
            WebAppContext root = new WebAppContext();

            root.setContextPath("/");
            root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
            root.setResourceBase(webappDirLocation);
            root.setParentLoaderPriority(true);

            server.setHandler(root);
            server.start();

            return this;
        }

        private void join() throws InterruptedException {
            server.join();
        }

        public void stop() throws Exception {
            server.stop();
        }
    }
}