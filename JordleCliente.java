import java.rmi.ConnectException;
import java.rmi.Naming;
import java.util.Scanner;

public class JordleCliente {
  public static void main(String[] args) {
    GameStats game;
    String wordToTry;
    Jordle jordle; // (1)
    Scanner scanner = new Scanner(System.in);
    int inputSessionId;

    try {
      jordle = (Jordle) Naming.lookup("rmi://127.0.0.1:1099/Jordle"); // (2)
      
      if (args.length > 0) {
        inputSessionId = Integer.parseInt(args[0]);
      } else {
        inputSessionId = -1;
      }

      game = jordle.getGame(inputSessionId); // (3)
      System.out.println(
        "Bem-vindo ao Jordle! Sua sessão é: " + Integer.toString(game.sessionId) + "\n\n" +
        "Você tem " + Integer.toString(5 - game.tries) + " tentativas. \n\n" + game.wordToTry + "\n" + game.mask);
      while (game.running && scanner.hasNextLine()) {
        wordToTry = scanner.nextLine();
        game.setWordToTry(wordToTry);
        game = jordle.newTry(game);
        System.out.println(game.mask);
        System.out.println();
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
}

/*
(1) Uma referência local ao serviço desejado.

(2) Retorna a referência a um stub para o objeto remoto. Toda a identificação do serviço desejado deve ser apresentada (o host, a porta e o nome fantasia).

(3) A invocação é feita no stub, resposável por invocar o serviço remoto.
*/
