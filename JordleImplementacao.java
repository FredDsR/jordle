import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class JordleImplementacao // (1)
             extends UnicastRemoteObject implements Jordle {

  static HashMap<Session, GameStats> activeGames = new HashMap<Session, GameStats>(); 

  public JordleImplementacao() throws RemoteException {
    super(); // (2)
  }
  
  static public void showActiveGames(){
    System.out.println("Sessões ativas: " + activeGames.keySet().toString());
  }

  public GameStats newGame() throws RemoteException {
    // TODO: Implementar resgate de sessão ativa
    GameStats game = new GameStats();
    System.out.println("Novo jogo iniciado com a sessão: " + Integer.toString(game.session.id));
    activeGames.put(game.session, game);
    showActiveGames();
    return game;
  }

  public GameStats newTry(GameStats game) throws RemoteException {
    System.out.println("Nova tentativa na sessão " + Integer.toString(game.session.id) + " com a palavra: " + game.wordToTry);
    showActiveGames();
    
    for (int i = 0; i < game.word.length(); i++) {
      if (game.wordToTry.contains(Character.toString(game.word.charAt(i)))) {
        //TODO implementar lógica para alterar mascara quando a letra existe
      }
      
      if (game.word.charAt(i) == game.wordToTry.charAt(i)) {
        // TODO implementar lógica para alterar mascara quando da match 
      }
    }
    
    return game;
  }
}
