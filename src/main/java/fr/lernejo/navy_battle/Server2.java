package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.util.UUID;

public class Server2 extends Server {
    private final Option<ServerInfo> localServer = new Option<>();

    @Override
    public void startServer(int port, String connectURL) throws IOException {
        localServer.set(new ServerInfo(
            UUID.randomUUID().toString(),
            "http://localhost:" + port,
            "hello world !"
        ));



        super.startServer(port, connectURL);
    }
    @Override
    public void createContextes(HttpServer server) {

    }

}
