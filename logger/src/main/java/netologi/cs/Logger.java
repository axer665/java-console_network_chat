package netologi.cs;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static String directoryPath = "src\\main\\resources\\logs";
    private String fileName;

    public Logger (String filename) {
        File directory = new File(directoryPath);
        if (directory.exists()) {
            System.out.println("Директория для файлов логов существует");
        } else if (directory.mkdirs()) {
            System.out.println("Директория для файлов логов создана");
        } else {
            System.out.println("Невозмоэно получить доступ к требуемой директории");
            return;
        }
        this.fileName = filename + ".txt";
        File file = new File(this.directoryPath + "\\" + this.fileName);
        try {
            if (file.exists()) {
                System.out.println("Файл логирования уже существует");
            } else if (file.createNewFile()) {
                System.out.println("Создан файл логирования");
            }
        } catch (IOException e) {

        }
    }

    public void addMessage(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.directoryPath + "\\" + this.fileName, true))) {
            bw.write(message);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String dateTime(DateTimeFormat datetime) {
        Date time = new Date(); // текущая дата
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); // форматируем дату и время
        if (datetime == DateTimeFormat.date) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss : SSS"); // форматируем дату и время
        }
        String dateTime = dateFormat.format(time); // дата и время в нужном формате
        return dateTime;
    }

    public boolean checkFile() {
        File file = new File(directoryPath + "\\" + this.fileName);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static void setDirectoryPath(String newDirectoryPath) {
        directoryPath = newDirectoryPath;
    }
    public static String getDirectoryPath() {
        return directoryPath;
    }

    public void renameFile(String fileName) {
        this.fileName = fileName + ".txt";

        File file = new File(directoryPath + "\\" + this.fileName + ".txt");
        File rename = new File(directoryPath + "\\" + fileName + ".txt");

        boolean flag = file.renameTo(rename);
        if (flag == true) {
            System.out.println("Файл логов переименован с " + this.fileName + ".txt на " + fileName + ".txt");
        } else {
            System.out.println("Переименование файла логов не удалось");
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFilePath() {
        return directoryPath + "\\" + this.fileName;
    }
}
