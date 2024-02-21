import java.util.*;
import java.io.Serializable;

public class Session implements Serializable{
    ArrayList<Integer> sessions = new ArrayList<Integer>();
    int id, max = 100;

    public Session() {
        id = this.newSession();
    }

    private Integer newSession(){
        int rand = new Random().nextInt();
        while (this.sessions.contains(rand)) {
            rand = new Random().nextInt(max + 1);
        }
        this.sessions.add(rand);
        return rand;
    }
}
