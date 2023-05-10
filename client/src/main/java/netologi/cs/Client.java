package netologi.cs;

import java.io.*;
import java.net.Socket;

public class Client {
    private Logger logger;

    private Socket socket;
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет
    private BufferedReader userInput; // поток чтения из консоли
    private String userName; // имя пользователя

    public Client(String host, int port) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("Ошибка создания сокета");
        }
        try {
            userInput = new BufferedReader(new InputStreamReader(System.in)); // поток чтения из консоли
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // поток чтения из сокета
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // поток записи в сокет
            this.pressNickname(); // перед началом необходимо спросить имя пользователя
            new MessageRead().start(); // соединение, читающее сообщения из сокета в бесконечном цикле
            new MessageWrite().start(); // соединение, пишущее сообщения из консоли в сокет
        } catch (IOException e) {
            Client.this.downWorker(); // Сокет быудет закрыт при любой ошибке
        }
    }

    private void pressNickname() {
        System.out.print("Введите своё имя: ");
        try {
            userName = userInput.readLine();
            logger = new Logger(userName);
            System.out.println("Привет, " + userName);
            out.write("К чату присоединился пользователь, " + userName + "\n");
            out.flush();
            logger.addMessage("CLIENT - ("+Logger.dateTime(DateTimeFormat.date)+") ### я подключился к чату \n");
        } catch (IOException e) {}
    }

    // отключение от сервера / закрытие сокета
    public void downWorker() {
        try {
            if (!socket.isClosed()) {
                out.write("Пользователь " + this.userName + " отсоединился");
                out.flush();
                logger.addMessage("CLIENT - ("+Logger.dateTime(DateTimeFormat.date)+") ### я отключился от чата \n");
                in.close(); // закрываем поток чтения
                out.close(); // закрываем поток записи
                socket.close(); // закрываем сокет
            }
        } catch (IOException e) {}
    }

    // получение сообщений от сервера
    private class MessageRead extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (true) {
                    str = in.readLine(); // ждем сообщение от сервера
                    System.out.println(str); // выводим сообщение от сервера в консоль
                    logger.addMessage("SERVER - ("+Logger.dateTime(DateTimeFormat.date)+") ### " + str + "\n"); // логируем
                }
            } catch (IOException e) {
                Client.this.downWorker();
            }
        }
    }

    // отправляем сообщение на сервер
    public class MessageWrite extends Thread {
        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    userWord = userInput.readLine(); // сообщение из консоли
                    if (userWord.equals("exit")) {
                        Client.this.downWorker();
                        break; // выходим из цикла если пришло "exit"
                    } else {
                        out.write("(" + Logger.dateTime(DateTimeFormat.dateTime) + ") " + userName + ": " + userWord + "\n"); // отправляем на сервер
                    }
                    logger.addMessage("CLIENT - ("+Logger.dateTime(DateTimeFormat.date)+") ### я отправил на сервер сообщение : " + userWord + " \n");
                    out.flush(); // чистим
                } catch (IOException e) {
                    Client.this.downWorker();
                }
            }
        }
    }
}
