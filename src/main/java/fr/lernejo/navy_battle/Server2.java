package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class Server2 extends Server {
    private final Option<ServerInfo> localServer = new Option<>();
    private final Option<ServerInfo> remoteServer = new Option<>();
    protected final Option<Game> game = new Option<>();

    @Override
    public void initServer(int port, String connectURL) throws IOException {
        localServer.set(new ServerInfo(
            UUID.randomUUID().toString(),
            "http://localhost:" + port,
            "hello world !"
        ));

        if (connectURL != null)
            new Thread(() -> this.requestStart(connectURL)).start();


        super.initServer(port, connectURL);
    }
    @Override
    public void createContextes(HttpServer server) {
        server.createContext("/api/game/start", s -> start(new RequestHandler(s)));
        server.createContext("/api/game/fire", s -> handleFire(new RequestHandler(s)));

    }
    public void start(RequestHandler handler) throws IOException {
        try {
            remoteServer.set(ServerInfo.fromJSON(handler.getJSONObject()));
            game.set(new Game());
            System.out.println("Vous allez vous battre contre " + remoteServer.get().getUrl());

            handler.sendJSON(202, localServer.get().toJSON());

            fire();


        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
    public void requestStart(String server) {
        try {
            game.set(new Game());
            this.remoteServer.set(new ServerInfo("temp", server, "good luck"));
            var response = POSTRequest(server + "/api/game/start", this.localServer.get().toJSON());

            this.remoteServer.set(ServerInfo.fromJSON(response).withURL(server));
            System.out.println("Vous allez vous battre contre " + remoteServer.get().getUrl());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Echec lors du lancement du jeu!");
        }
    }
    public void fire() throws IOException, InterruptedException {
        Coordinates coordinates = game.get().getNextPlaceToHit();
        var response =
            GETRequest(remoteServer.get().getUrl() + "/api/game/fire?cell=" + coordinates.toString());

        if (!response.getBoolean("shipLeft")) {
            game.get().wonGame();
            return;
        }

        game.get().setFireResult(coordinates, Result.fromAPI(response.getString("consequence")));
    }

    public void handleFire(RequestHandler handler) throws IOException {
        try {
            var pos = new Coordinates(handler.getQueryParameter("cell"));
            handler.sendJSON(200, new JSONObject().put("consequence", game.get().hit(pos).toAPI())
                .put("shipLeft", game.get().localMapShipLeft()));

            if (!game.get().localMapShipLeft()) {
                System.out.println("Nous avons perdu le jeu ;(");
                return;
            }

            fire();
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }



}
