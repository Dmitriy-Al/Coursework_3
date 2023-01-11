import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;


public class WorkServer {

    public synchronized void serverRun(int port) {
        LinkedBlockingDeque<Message> text = new LinkedBlockingDeque<>();

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Запуск сервера...");

            while (true) {
                Socket socket = serverSocket.accept();
                Connector<Message> connector = new Connector<>(socket);

                    new Thread(() -> {
                        Message message;
                        try {
                            message = connector.getMessage();
                            text.addLast(message);
                            text.addFirst(message);
                            System.out.println("Сообщение клиента: " + message.getText());
                        } catch (IOException | ClassNotFoundException e) {
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

        WorkServer server = new WorkServer();
        server.serverRun(8000);

    }

}