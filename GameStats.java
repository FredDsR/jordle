import java.io.Serializable;

public class GameStats implements Serializable {
    String[] words, masks, wordsToTry; 
    int tries, sessionId, gamesQty;
    boolean running, winner;
    boolean[] winStates;

    public GameStats(int session_id, int gamesQty){
        this.sessionId = session_id;
        this.tries = 0;
        this.gamesQty = gamesQty;
        this.winStates = new boolean[this.gamesQty];
        this.words = this.getWords();
        this.masks = this.initMasks();
        this.wordsToTry = this.initWordsToTry();
        this.running = true;
        this.winner = false;

        for (int i = 0; i < this.gamesQty; i++) {
            this.winStates[i] = false;
        }
    }

    public void setWordsToTry(String[] wordsToTry) {
        this.wordsToTry = wordsToTry;
    }

    public boolean didIWin(){
        return winner;
    }

    public String[] getWords(){
        String[] words = new String[this.gamesQty];
        for (int i = 0; i < this.gamesQty; i++) {
            words[i] = "teste";
        }
        return words;
    }

    public String[] initMasks(){
        String[] masks = new String[this.gamesQty];
        for (int i = 0; i < this.gamesQty; i++) {
            masks[i] = "0".repeat(5);
        }
        return masks;
    }

    public String[] initWordsToTry(){
        String[] wordsToTry = new String[this.gamesQty];
        for (int i = 0; i < this.gamesQty; i++) {
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
            "-> words:\t" + this.words.toString() + "\n" +
            "-> wordsToTry:\t" + this.wordsToTry.toString() + "\n" +
            "-> masks:\t" + this.masks.toString() + "\n" +
            "-> winStates:\t" + this.winStates.toString()
        );
    }
}
