import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JordleImplementacao // (1)
             extends UnicastRemoteObject implements Jordle {
public JordleImplementacao() throws RemoteException {
    super(); // (2)
  }
  
  public GameStats newGame() throws RemoteException {
    GameStats game = new GameStats();
    System.out.println("Novo jogo iniciado com a sessão: " + Integer.toString(game.session.id));
    return game;
  }

  public GameStats newTry(GameStats game) throws RemoteException {
    System.out.println("Nova tentativa na sessão " + Integer.toString(game.session.id) + " com a palavra: " + game.wordToTry);
    return game;
  }
}
