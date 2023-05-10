package netologi.cs;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private Logger logger;
    private int port;
    boolean stop = false; // если нам потребуется остановить сервер

    public List<ServerWorker> connectionList = new ArrayList<>(); // список соединений

    public Server (int port) {
        this.port = port;
        logger = new Logger("Server"+port);
        logger.addMessage("(" + Logger.dateTime(DateTimeFormat.date)+ ") - Сервер создан. \n");
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(this.port);
            logger.addMessage("(" + Logger.dateTime(DateTimeFormat.date)+ ") - Сервер запущен на порту " + port + "\n");
            try {
                while (true) {
                    if (this.stop == true){
                        System.out.println("Сервер принудительно остановлен.");
                        logger.addMessage("(" + Logger.dateTime(DateTimeFormat.date)+ ") - Сервер прекратил работу принудительно \n");
                        server.close();
                        break;
                    }
                    Socket socket = server.accept(); // блокируем до возникновения нового соединения
                    ServerWorker ss = new ServerWorker(socket, this); // создаем новое соединение
                    connectionList.add(ss); // добавиляем соединенние в список
                    System.out.print("Клиент присоединился: ");
                    System.out.println(ss.getConnection());

                    logger.addMessage("(" + Logger.dateTime(DateTimeFormat.date)+ ") - Новое соединение : " + ss.getConnection() + "\n");
                }
            } finally {
                server.close();
                logger.addMessage("(" + Logger.dateTime(DateTimeFormat.date)+ ") - Сервер прекратил работу \n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        return this.port;
    }

    public void stop() {
        this.stop = true;
    }
}
