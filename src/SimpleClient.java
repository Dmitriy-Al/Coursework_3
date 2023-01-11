import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {

    public void Chat(String ip, int port) {
        System.out.println("Чат открыт.");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try (Connector<Message> connector = new Connector<>(new Socket(ip, port))) {
                System.out.println("Введите сообщение (\"esc\" - завершить чат)");
                String clientMessage = scanner.nextLine();
                if (clientMessage.equals("esc")) return;
                Message message = new Message(clientMessage);
                connector.sendMessage(message);
                System.out.println("Сообщение отправлено...");
                message = connector.getMessage();
                System.out.println("Сообщение сервера: " + message.getText());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Err");
            }
        }
    }


    public static void main(String[] args) {

        SimpleClient client = new SimpleClient();
        client.Chat("127.0.0.1", 8000);

    }
}