import java.util.Timer;

public class MainClass {
    public static void main(String[] args){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate( new RetrieveSendTask(), 0,60000);
    }
}
