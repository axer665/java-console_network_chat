package netologi.cs;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Settings settings = new Settings();
        Server server = new Server(settings.getPort());
        server.start();
    }
}