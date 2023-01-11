import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread {

    public synchronized void chat(String ip, int port) {
        System.out.println("Чат запущен...");

        ClientSender clientSender = new ClientSender(ip, port);
        Thread senderThread = new Thread(clientSender);;
        senderThread.start();

        ClientReceiver clientReceiver = new ClientReceiver(ip, port);
        Thread receiverThread = new Thread(clientReceiver);
        receiverThread.start();

    }



    public static void main(String[] args) {

        ClientThread clien = new ClientThread();
        clien.chat("127.0.0.1", 8000);

    }


    public class ClientSender implements Runnable {

        private String ip;
        private int port;

        public ClientSender(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public synchronized void run() {
            Scanner scanner = new Scanner(System.in);

           while (true) {
                System.out.println("Введите сообщение (\"esc\" - завершить чат)");
                String clientMessage = scanner.nextLine();
                try {Connector<Message> senderConnector = new Connector<>(new Socket(ip, port));
                    if(clientMessage.equals("esc")) return;
                    Message message = new Message(clientMessage);
                    senderConnector.sendMessage(message);
                } catch (IOException e) {
                    System.out.println("Err output " + e.getMessage());
                }
            }
        }
    }


    public class ClientReceiver implements Runnable {

        private String ip;
        private int port;

        public ClientReceiver(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public synchronized void run() {
            while (true) {
                try {Connector<Message> receiveConnector = new Connector<>(new Socket(ip, port));
                    Message receiveMessage = receiveConnector.getMessage();
                    System.out.println("*Сообщение от: " + receiveMessage.getName() + "\n*текст: " + receiveMessage.getText() + "\n*дата и время: " + receiveMessage.getTime());
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Err input " + e.getMessage());
                }
            }
        }
    }


}