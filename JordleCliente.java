import java.rmi.ConnectException;
import java.rmi.Naming;
import java.util.Scanner;

public class JordleCliente {
  public static void main(String[] args) {
    GameStats game;
    String wordToTry;
    Jordle jordle; // (1)
    Scanner scanner = new Scanner(System.in);
    
    try {
      jordle = (Jordle) Naming.lookup("rmi://127.0.0.1:1099/Jordle"); // (2)
      game = jordle.newGame(); // (3)
      System.out.println(
        "Bem-vindo ao Jordle! Sua sessão é: " + Integer.toString(game.session.id) + "\n\n" +
        "A palavra selecionada para você tem " + game.word.length() + " letras e você tem 5 tentativas. \n\n");
      while (game.running && scanner.hasNextLine()) {
        wordToTry = scanner.nextLine();
        game.setWordToTry(wordToTry);
        game = jordle.newTry(game);
        System.out.println(game.mask);
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
