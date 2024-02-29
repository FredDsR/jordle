import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class JordleImplementacao // (1)
             extends UnicastRemoteObject implements Jordle {

  static HashMap<Integer, GameStats> activeGames = new HashMap<Integer, GameStats>(); 
  static private int maxGames = 100;

  public JordleImplementacao() throws RemoteException {
    super(); // (2)
  }
  
  static public void showActiveGames(){
    System.out.println("Sessões ativas: " + activeGames.keySet().toString());
    for (GameStats game : activeGames.values()) {
      System.out.println(game);
    }
  }

  public static String changeChar(String str, int idx, char newChar) {
    if (idx < 0 || idx >= str.length()) {
        throw new IllegalArgumentException("Posição inválida");
    }

    char[] chars = str.toCharArray(); // Convertendo a string para um array de caracteres
    chars[idx] = newChar; // Alterando o caractere na posição especificada
    return new String(chars); // Convertendo o array de caracteres de volta para uma string
}

  private Integer newSession(){
      int rand = new Random().nextInt(maxGames + 1);
      while (activeGames.containsKey(rand)) {
          rand = new Random().nextInt(maxGames + 1);
      }
      return rand;
  }

  private GameStats fetchGame(int sessionId, int gamesQty) throws RemoteException {
    GameStats game;
    int newSession;

    if (sessionId == -1) {
      newSession = this.newSession();
      game = new GameStats(newSession, gamesQty);
      activeGames.put(newSession, game);
      System.out.println("Novo jogo iniciado com a sessão: " + Integer.toString(game.sessionId));
      return game;
    }

    if (activeGames.containsKey(sessionId)) {
      game = activeGames.get(sessionId);
      System.out.println("Jogo recuperado com a sessão: " + Integer.toString(game.sessionId));
      return game;
    }

    throw new RemoteException("Foi mal... A sessão " + Integer.toString(sessionId) + " não existe.");
  }

  public GameStats getGame(int sessionId, int gamesQty) throws RemoteException {
    GameStats game = fetchGame(sessionId, gamesQty);
    showActiveGames();
    return game;
  }

  public GameStats newTry(GameStats game) throws RemoteException {
    System.out.println("Nova tentativa na sessão " + Integer.toString(game.sessionId) + " com as palavras: " + Arrays.toString(game.wordsToTry));

    for (int i = 0; i < game.gamesQty; i++) {
      game.masks[i] = this.checkWord(game.words[i], game.wordsToTry[i]);
      
      // Controla vitória no jogo
      if (game.masks[i].equals("22222")) {
        game.winStates[i] = true;
      }
    }

    if (this.checkWins(game.winStates)) {
      game.winner = true;
      game.running = false;
      activeGames.remove(game.sessionId);
    }

    // Controla tentativas do jogo
    game.tries += 1;

    if (game.tries >= 5) {
      game.running = false;
      activeGames.remove(game.sessionId);
    }

    // activeGames.remove(game.sessionId);
    activeGames.put(game.sessionId, game);
    showActiveGames();
    return game;
  }

  private HashMap<Character, Integer> getCountCharMap(String word) {
    char c;
    HashMap<Character, Integer> countCharMap = new HashMap<Character, Integer>();
    
    for (int i = 0; i < word.length(); i++) {
      c = word.charAt(i);
      if (countCharMap.containsKey(c)) {
        countCharMap.put(c, countCharMap.get(c) + 1);
      } else {
        countCharMap.put(c, 1);
      }
    }
    return countCharMap;
  }

  private boolean checkWins(boolean[] winStates) {
    for (boolean value : winStates) {
        if (!value) {
            return false;
        }
    }
    return true;
  } 

  private String checkWord(String word, String wordToTry) throws RemoteException{
    String mask = "00000";
    
    if (wordToTry.length() != 5) {
      throw new RemoteException("A palavra " + wordToTry + " não tem 5 caracteres.");
    }

    // Controla a lógica da máscara
    HashMap<Character, Integer> countCharMap = getCountCharMap(word);
    for (int i = 0; i < word.length(); i++) {
      char trueChar = word.charAt(i);
      char tryChar = wordToTry.charAt(i);

      if (trueChar != tryChar) {
        mask = changeChar(mask, i, '0');
      }
      
      if (trueChar == tryChar) {
        mask = changeChar(mask, i, '2');
        countCharMap.put(trueChar, countCharMap.get(trueChar) - 1);
      }
      
      if (countCharMap.containsKey(tryChar) && countCharMap.get(tryChar) > 0) {
        mask = changeChar(mask, i, '1');
        countCharMap.put(trueChar, countCharMap.get(trueChar) - 1);
      }
      
    }

    return mask;
  }

}
