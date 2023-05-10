import netologi.cs.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;

public class ServerTest {
    Server server = new Server(4004);

    @Test
    public void testsConnection() throws IOException, InterruptedException {

        boolean reachable = false;

        new Thread(()->{
            server.start();
        }).start();

        TimeUnit.SECONDS.sleep(1);

        try (Socket pingSocket = new Socket("127.0.0.1", 4004)) {
            try (PrintWriter out = new PrintWriter(pingSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));){
                out.println("ping");
                reachable = true;
            }
        }

        server.stop();

        // given:
        boolean expected = reachable;
        // when:
        var result = true;

        // then:
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testNotConnection () {
        boolean reachable = false;
        try (Socket pingSocket = new Socket("127.0.0.1", 4004)) {
            try (PrintWriter out = new PrintWriter(pingSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));){
                out.println("ping");
                reachable = true;
            }
        } catch (UnknownHostException e) {

        } catch (IOException e) {

        }
        boolean expected = reachable;
        // when:
        var result = false;

        // then:
        Assertions.assertEquals(expected, result);
    }
}
