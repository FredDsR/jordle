import java.rmi.ConnectException;
import java.rmi.Naming;
import java.util.Scanner;

public class JordleCliente {
  public static void main(String[] args) {
    GameStats game;
    String wordsToTry;
    Jordle jordle;
    Scanner scanner = new Scanner(System.in);
    int inputSessionId;

    try {
      jordle = (Jordle) Naming.lookup("rmi://127.0.0.1:1099/Jordle"); 
      
      if (args.length > 0) {
        inputSessionId = Integer.parseInt(args[0]);

      } else {
        inputSessionId = -1;
      }

      game = jordle.getGame(inputSessionId);

      System.out.println(
        "Bem-vindo ao Jordle! Sua sessão é: " + Integer.toString(game.sessionId) + "\n\n");
      
        printGameState(game);

        while (game.running && scanner.hasNextLine()) {
        wordsToTry = scanner.nextLine();
        game.setWordsToTry(formatWordsToTry(wordsToTry, game));
        game = jordle.newTry(game);
        printGameState(game);
      }
      if (game.didIWin()) {
        System.out.println("Você acertou! Um verdadeiro jênio!");
      } else {
        System.out.println("Demorou nessa hein. Tente de novo!");
      }
      scanner.close();
    } catch (ConnectException e) {
      System.out.println("ERRO: Vish... Parece que o servidor caiu :/ ");
    } catch (Exception e) {
      System.out.println("Erro Interno. Nossa culpa, não esquenta. Erro: " + e);
    }
  }

  static private String[] formatWordsToTry(String entry, GameStats game){
    return entry.split("\\s+");
  } 

  static private void printGameState(GameStats game){
    if (game.winner) {
      System.out.println("Parabéns! Você é um verdadeiro Jênio!");
      return;
    }

    System.out.println("Tentativa: " + Integer.toString(game.tries) + "/5");
    System.out.println(concat(game.words));
    System.out.println(concat(game.masks));
  }

  public static String concat(String[] arrayDeStrings) {
    StringBuilder result = new StringBuilder();
    
    for (int i = 0; i < arrayDeStrings.length; i++) {
        result.append(arrayDeStrings[i]);
        
        // Adiciona um espaço se não for o último elemento do array
        if (i < arrayDeStrings.length - 1) {
            result.append(" ");
        }
    }
    
    return result.toString();
}
}

