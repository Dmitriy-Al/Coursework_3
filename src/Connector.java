import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connector<T extends Message> implements AutoCloseable{

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Connector(Socket socket) throws IOException {
        this.socket = socket;
        objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
        objectInputStream = new ObjectInputStream(this.socket.getInputStream());
    }

    public void sendMessage(T t) throws IOException{
        objectOutputStream.writeObject(t);
        objectOutputStream.flush();
    }

    public T getMessage() throws IOException, ClassNotFoundException{
        T t = (T) objectInputStream.readObject();
        return t;
    }

    @Override
    public void close() throws IOException{
        socket.close();
        objectOutputStream.close();
        objectInputStream.close();
    }

}
