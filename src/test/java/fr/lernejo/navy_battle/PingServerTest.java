package fr.lernejo;

import fr.lernejo.navy_battle.ScenariosTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PingServerTest extends ServerTest {
    @Test
    public void testPing() throws Exception {
        int port = getRandomPort(0);
        var test = new ScenariosTest(port);
        try {
            waitForPortToBeAvailable(port);
            assertEquals("OK", doGet(port, "ping"));
        } finally {
            test.stop();
        }

    }
}
