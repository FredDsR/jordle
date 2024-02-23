import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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

  private GameStats fetchGame(int sessionId) throws RemoteException {
    GameStats game;
    int newSession;

    if (sessionId == -1) {
      newSession = this.newSession();
      game = new GameStats(newSession);
      activeGames.put(newSession, game);
      return game;
    } 

    if (activeGames.containsKey(sessionId)) {
      return activeGames.get(sessionId);
    }

    throw new RemoteException("Foi mal... A sessão " + Integer.toString(sessionId) + " não existe.");
  }

  public GameStats getGame(int sessionId) throws RemoteException {
    GameStats game = fetchGame(sessionId);
    System.out.println("Novo jogo iniciado com a sessão: " + Integer.toString(game.sessionId));
    showActiveGames();
    return game;
  }

  public GameStats newTry(GameStats game) throws RemoteException {
    System.out.println("Nova tentativa na sessão " + Integer.toString(game.sessionId) + " com a palavra: " + game.wordToTry);
    showActiveGames();
    
    if (game.wordToTry.length() != 5) {
      throw new RemoteException("Palavra não tem 5 caracteres.");
    }

    // Controla a lógica da máscara
    HashMap<Character, Integer> countCharMap = getCountCharMap(game.word);
    for (int i = 0; i < game.word.length(); i++) {
      
      char trueChar = game.word.charAt(i);
      char tryChar = game.wordToTry.charAt(i);

      if (trueChar != tryChar) {
        game.mask = changeChar(game.mask, i, '0');
      }

      System.out.println(Character.toString(tryChar) + " " + countCharMap.toString());
      
      if (countCharMap.containsKey(tryChar) && countCharMap.get(tryChar) > 0) {
        game.mask = changeChar(game.mask, i, '1');
        countCharMap.put(trueChar, countCharMap.get(trueChar) - 1);
      }
      
      if (trueChar == tryChar) {
        game.mask = changeChar(game.mask, i, '2');
      }
    }

    // Controla vitória no jogo
    if (game.mask.equals("22222")) {
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

}
