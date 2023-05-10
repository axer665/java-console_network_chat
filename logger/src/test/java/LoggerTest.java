import netologi.cs.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoggerTest {
    Logger logger = new Logger("testLog");
    @Test
    public void testCreateFile() {
        // given:
        String expected = logger.getFileName();
        // when:
        String result = "testLog.txt";
        // then:
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testDirectory() {
        Logger.setDirectoryPath("src\\main\\resources\\testLogs");
        // given:
        String expected = Logger.getDirectoryPath();
        // when:
        String result = "src\\main\\resources\\testLogs";
        // then:
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testRename() {
        Logger loggerForRename = new Logger("nameTestLog");
        loggerForRename.renameFile("renameLog");
        // given:
        String expected = loggerForRename.getFileName();
        // when:
        String result = "renameLog.txt";
        // then:
        Assertions.assertEquals(expected, result);
    }
}
