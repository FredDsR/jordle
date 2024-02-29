import java.io.Serializable;

public class GameStats implements Serializable {
    String[] words, masks, wordsToTry; 
    int tries, sessionId, games_qty;
    boolean running, winner;

    public GameStats(int session_id, int games_qty){
        this.sessionId = session_id;
        this.tries = 0;
        this.games_qty = games_qty;
        this.words = this.getWords();
        this.masks = this.initMasks();
        this.wordsToTry = this.initWordsToTry();
        this.running = true;
        this.winner = false;
    }

    public void setWordsToTry(String[] wordsToTry) {
        this.wordsToTry = wordsToTry;
    }

    public boolean didIWin(){
        return winner;
    }

    public String[] getWords(){
        String[] words = new String[this.games_qty];
        for (int i = 0; i < this.games_qty; i++) {
            words[i] = "teste";
        }
        return words;
    }

    public String[] initMasks(){
        String[] masks = new String[this.games_qty];
        for (int i = 0; i < this.games_qty; i++) {
            masks[i] = "0".repeat(5);
        }
        return masks;
    }

    public String[] initWordsToTry(){
        String[] wordsToTry = new String[this.games_qty];
        for (int i = 0; i < this.games_qty; i++) {
            wordsToTry[i] = "-".repeat(5);
        }
        return wordsToTry;
    }

    public String toString(){
        return (
            "Game State:\n" + 
            "-> session:\t" + Integer.toString(this.sessionId) + "\n" +
            "-> tries:\t" + Integer.toString(this.tries) + "\n" +
            "-> running:\t" + Boolean.toString(this.running) + "\n" +
            "-> winner:\t" + Boolean.toString(this.winner) + "\n" +
            "-> words:\t" + this.words + "\n" +
            "-> wordsToTry:\t" + this.wordsToTry + "\n" +
            "-> masks:\t" + this.masks
        );
    }
}
