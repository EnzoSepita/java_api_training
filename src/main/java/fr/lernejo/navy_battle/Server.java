package fr.lernejo.navy_battle;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
//import java.net.http.HttpClient;
import java.util.concurrent.Executors;


public class Server {
   // protected final HttpClient client = HttpClient.newHttpClient();

    public void startServer(int port, String connectURL) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", this::handlePing);
        server.start();
    }

   /* public void stopServer() {
        this.server.get().stop(0);
    }*/

    private void handlePing(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }

}
