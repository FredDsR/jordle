import java.io.Serializable;

public class GameStats implements Serializable {
    String word, mask, wordToTry; 
    int tries;
    Session session;
    boolean running, winner;

    public GameStats(){
        session = new Session();
        tries = 0;
        word = this.getWord();
        mask = "0".repeat(word.length());
        running = true;
        winner = false;
    }

    public void setWordToTry(String wordToTry) {
        this.wordToTry = wordToTry;
    }

    public boolean didIWin(){
        return winner;
    }

    public String getWord(){
        return "teste";
    }
}
