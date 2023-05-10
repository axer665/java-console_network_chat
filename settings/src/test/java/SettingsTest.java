import netologi.cs.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SettingsTest {
    @Test
    public void testFileName() {
        Settings settings = new Settings("testSettings.txt", "127.0.0.1", 4005);

        // given:
        String expected = settings.getFileName();

        // when:
        var result = "testSettings.txt";

        // then:
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testHost() {
        Settings settings = new Settings("testSettings.txt", "127.0.0.1", 4005);

        // given:
        String expected = settings.getHost();

        // when:
        var result = "127.0.0.1";

        // then:
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testPort() {
        Settings settings = new Settings("testSettings.txt", "127.0.0.1", 4005);

        // given:
        int expected = settings.getPort();

        // when:
        var result = 4005;

        // then:
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testLoadData() throws IOException, ClassNotFoundException {
        new Settings("testSettings1", "127.0.0.1", 4005);
        Settings settings = new Settings("testSettings2", "127.0.0.1", 4005);
        settings.loadData("testSettings1");

        // given:
        String expected = settings.getFileName();

        // when:
        var result = "testSettings1";

        // then:
        Assertions.assertEquals(expected, result);
    }
}
