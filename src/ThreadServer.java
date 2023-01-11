import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadServer {


    //private HashMap<Integer, Connector> clientBase = new HashMap<>();
    //private ArrayList<Connector> clientBase = new ArrayList<>();

    public synchronized void serverRun(int port) {
        ArrayBlockingQueue<Message> text = new ArrayBlockingQueue<>(100, true);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Запуск сервера...");
            while (true) {
                Socket socket = serverSocket.accept();
                Connector<Message> connector = new Connector<>(socket);
                new Thread(() -> {
                    Message message;
                    try {
                        System.out.println("t - 1");
                        message = connector.getMessage();
                        System.out.println("t - 2");
                        text.put(message);
                        System.out.println("t - 3");
                        System.out.println("Сообщение клиента: " + message.getText());
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

                new Thread(() -> {
                    try {
                            Message serverMessage = text.take();

                            connector.sendMessage(serverMessage);

                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public static void main(String[] args) {

        ThreadServer server = new ThreadServer();
        server.serverRun(8000);

    }

}
