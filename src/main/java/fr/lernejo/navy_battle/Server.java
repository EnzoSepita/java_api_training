package fr.lernejo.navy_battle;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;


public abstract class Server {
    protected final HttpClient client = HttpClient.newHttpClient();

    public void startServer(int port, String connectURL) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", this::handlePing);
        createContextes(server);
        server.start();
    }

   /* public void stopServer() {
        this.server.get().stop(0);
    }*/

    public abstract void createContextes(HttpServer server);

    private void handlePing(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }
    public JSONObject sendPOSTRequest(String url, JSONObject obj) throws IOException, InterruptedException {
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(obj.toString()))
            .build();

        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }

    /**
     * Send GET request
     */
    public JSONObject sendGETRequest(String url) throws IOException, InterruptedException {
        HttpRequest requeteGET = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Accept", "application/json")
            .GET()
            .build();

        var response = client.send(requeteGET, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }

}
