import netologi.cs.Client;
import netologi.cs.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ClientTest {
    Server server = new Server(4004);

    @Test
    public void testConnection() throws InterruptedException {
        Thread serverThread =  new Thread(()->{
            server.start();
        });
        serverThread.start();

        TimeUnit.SECONDS.sleep(1);

        Thread client1Thread = new Thread(()->{
            Client client1 = new Client("localhost", 4004);
            client1.downWorker();
        });

        Thread client2Thread = new Thread(()->{
            Client client2 = new Client("localhost", 4004);
            client2.downWorker();
        });

        client1Thread.start();
        client2Thread.start();

        TimeUnit.SECONDS.sleep(1);

        // given:
        int expected = server.connectionList.size();

        client1Thread.interrupt();
        client2Thread.interrupt();

        server.stop();

        // when:
        var result = 2;

        // then:
        Assertions.assertEquals(expected, result);
    }
}
