package fr.lernejo.navy_battle;


import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Usage: Launcher [port] {server_url}");
                System.exit(-1);
            }

            int port = Integer.parseInt(args[0]);
            System.out.println("Starting to listen on port " + port);

            new Server2().initServer(port, args.length > 1 ? args[1] : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
