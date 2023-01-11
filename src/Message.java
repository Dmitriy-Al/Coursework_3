import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Message implements Serializable, Delayed {

    private String text;
    private LocalDateTime time;
    private String name;

    public Message(String text){
        this.text = text;
        time = LocalDateTime.now();
        name = "Клиент " + Thread.currentThread().getId();

    }

    public String getText(){
        return text;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public String getName() {
        return name;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Duration.between(LocalDateTime.now(), time).getSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        LocalDateTime compareTime = ((Message) o).time;
        return this.time.compareTo(compareTime);
    }


}
