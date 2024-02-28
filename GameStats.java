import java.io.Serializable;

public class GameStats implements Serializable {
    String word, mask, wordToTry; 
    int tries, sessionId;
    boolean running, winner;

    public GameStats(int session_id){
        this.sessionId = session_id;
        this.tries = 0;
        this.word = this.getWord();
        this.mask = "0".repeat(word.length());
        this.wordToTry = "-".repeat(word.length());
        this.running = true;
        this.winner = false;
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

    public String toString(){
        return (
            "Game State:\n" + 
            "-> session:\t" + Integer.toString(this.sessionId) + "\n" +
            "-> tries:\t" + Integer.toString(this.tries) + "\n" +
            "-> running:\t" + Boolean.toString(this.running) + "\n" +
            "-> winner:\t" + Boolean.toString(this.winner) + "\n" +
            "-> word:\t" + this.word + "\n" +
            "-> wordToTry:\t" + this.wordToTry + "\n" +
            "-> mask:\t" + this.mask
        );
    }
}
