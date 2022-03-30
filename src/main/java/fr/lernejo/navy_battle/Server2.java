package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.util.UUID;

public class Server2 extends Server {
    private final Option<ServerInfo> localServer = new Option<>();
    private final Option<ServerInfo> remoteServer = new Option<>();

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
        server.createContext("/api/game/start", s -> startGame(new RequestHandler(s)));

    }
    public void startGame(RequestHandler handler) throws IOException {
        try {
            remoteServer.set(ServerInfo.fromJSON(handler.getJSONObject()));
            System.out.println("Vous allez vous battre contre " + remoteServer.get().getUrl());

            handler.sendJSON(202, localServer.get().toJSON());

        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }



}
