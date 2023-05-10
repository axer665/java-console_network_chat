package netologi.cs;

public class Main {
    public static void main(String[] args) {
        Settings settings = new Settings("settings.txt", "127.0.0.1", 4004);
        System.out.println(settings);
    }
}