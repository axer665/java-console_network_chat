package netologi.cs;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Settings settings = new Settings();
        System.out.println(settings);
        new Client(settings.getHost(), settings.getPort());
    }
}