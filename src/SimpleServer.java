import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

/*

    public synchronized void serverRun(int port) {

        String serverMessageText = "сервер принял сообщение";
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Запуск сервера...");
            while (true) {
                Socket socket = serverSocket.accept();
                Connector<Message> connector = new Connector<>(socket);
                new Thread(() -> {
                    Message message = null;
                    try {
                        message = connector.getMessage();
                        System.out.println("Сообщение клиента: " + message.getText());
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                new Thread(() -> {
                    Message serverMessage = new Message(serverMessageText);
                    try {
                        connector.sendMessage(serverMessage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 */


    public void serverRun(int port) { //ServerSocket работает с IO пакетом
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Запуск сервера...");
            while (true) {
                Socket socket = serverSocket.accept(); // программа останавливает работу в ожидании подключения
                // взаимодействие с клиентом
                Connector<Message> connector = new Connector<>(socket);

                Message fromClient = connector.getMessage();
                System.out.println("From client: " + fromClient);

                Message message = new Message("Сообщение сервера: ");
                connector.sendMessage(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Exceptions ");
        }
    }

    public static void main(String[] args) {

        ThreadServer server = new ThreadServer();
        server.serverRun(8000);

    }
}