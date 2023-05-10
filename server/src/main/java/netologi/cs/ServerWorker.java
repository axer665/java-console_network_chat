package netologi.cs;

import java.io.*;
import java.net.Socket;

public class ServerWorker extends Thread {

    private Logger logger;
    private Server server;
    private Socket socket; // сокет, через который сервер общается с клиентом
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    public ServerWorker(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.logger = new Logger("Server" + this.server.getPort());

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            start(); // вызываем run()
        } catch (IOException e) {

        }
    }

    @Override
    public void run() {
        String word;
        try {
            while (true) {
                word = in.readLine();
                if (word != null && word.equals("exit")) { // если пришло сообщение "exit", выходим из цикла
                    logger.addMessage("(" + Logger.dateTime(DateTimeFormat.date)+ ") - соединение " + this.getConnection() + " закрыто \n");
                    break;
                } else if (word != null) { // иначе логируем сообщение (цикл продолжается)
                    logger.addMessage("(" + Logger.dateTime(DateTimeFormat.date)+ ") - Сообщение от " + this.getConnection() + ": " + word + "\n");
                } else { // вероятнее всего, соединение разорвано
                    logger.addMessage("(" + Logger.dateTime(DateTimeFormat.date)+ ") - соединение " + this.getConnection() + " отключено \n");
                    this.socket.close();
                    break;
                }

                for (ServerWorker sw : server.connectionList) {
                    sw.send(word); // отправить принятое сообщение от текущего клиента всем клиентам
                }
            }

        } catch (IOException e) {
        }
    }

    private void send(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }

    public Socket getConnection () {
        return this.socket;
    }
}
