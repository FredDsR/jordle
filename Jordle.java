import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Jordle // (1)
                 extends Remote { 
  public GameStats getGame(int sessionId, int gamesQty) throws RemoteException;
  public GameStats newTry(GameStats game) throws RemoteException;
}

/*
(1) Esta classe define a interface do serviço proposto.
*/

