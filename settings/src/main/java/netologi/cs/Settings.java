package netologi.cs;

import java.io.*;

public class Settings implements Serializable {
    private static String directoryPath = "src\\main\\resources\\settings";
    private String fileName = "mainSettings.txt";
    private String host = "localhost";
    private int port = 4004;

    public Settings () throws IOException, ClassNotFoundException {
        this.saveData();
        this.loadData(this.fileName);
    }

    public Settings(String fileName, String host, int port) {
        this.host = host;
        this.port = port;
        this.fileName = fileName;
        this.saveData();
    }

    private void saveData () {
        File directory = new File(this.directoryPath);
        if (directory.exists()) {
            System.out.println("Директория для файлов настроек существует");
        } else if (directory.mkdirs()) {
            System.out.println("Директория для файлов настроек создана");
        } else {
            System.out.println("Невозмоэно получить доступ к требуемой директории");
            return;
        }

        File file = new File(directory + "\\" + fileName);

        try {
            if (file.exists()) {
                System.out.println("Файл настроек существует");
            } else if (file.createNewFile()) {
                System.out.println("Файл настроек успешно создан");
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (IOException e) {

        }

    }

    public void loadData (String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(this.directoryPath + "\\" + fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Settings settings = (Settings) objectInputStream.readObject();

        this.fileName = settings.getFileName();
        this.host = settings.getHost();
        this.port = settings.getPort();
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getPort() {
        return this.port;
    }

    public String getHost() {
        return this.host;
    }

    public String toString() {
        return "clientServerSettings{" +
                "fileName=" + this.fileName +
                ",host=" + this.host +
                ",port=" + this.port +
                '}';
    }
}
