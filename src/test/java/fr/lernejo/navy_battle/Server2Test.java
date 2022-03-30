package fr.lernejo;

import fr.lernejo.navy_battle.RequestHandler;
import fr.lernejo.navy_battle.Server;
import fr.lernejo.navy_battle.Server2;
import fr.lernejo.navy_battle.Status;

import java.io.IOException;

public class Server2Test extends Server2 implements AutoCloseable {

    final Object lock = new Object();

    @Override
    public void fire() throws IOException, InterruptedException {
        super.fire();
        checkForEnd();
    }

    @Override
    public void handleFire(RequestHandler handler) throws IOException {
        super.handleFire(handler);
        checkForEnd();
    }

    private boolean gameEnded() {
        return game.get().getStatus() != Status.ONGOING;
    }

    private void checkForEnd() {
        if (gameEnded())
            stopServer();
    }

    @Override
    public void stopServer() {
        super.stopServer();

        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    public void close() throws Exception {
        super.stopServer();
    }

    public void waitForEndOfGame() throws InterruptedException, Exception {
        synchronized (lock) {
            lock.wait(5000);

            if (!gameEnded())
                throw new RuntimeException("The game did not complete in allowed time!");
        }
    }
}
