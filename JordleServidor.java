import java.rmi.*;

public class JordleServidor {
  public static void main(String[] args) throws RemoteException {
    try {
      JordleImplementacao f = new JordleImplementacao(); // (1)
      Naming.rebind("rmi://127.0.0.1:1099/"+"Jordle", f); // (2)
      System.out.println("Pronto para novos jordles hehehe"); // (3)
    } catch (Exception e) { // (4)
        System.err.println("Exceção RMI:");
        e.printStackTrace();
    }
  }
}
/*
(1) Aqui está sendo criada uma instância da classe que efetivamente executa o serviço proposto.

(2) Neste ponto é associado ao objeto serviço uma identificação composta de três elementos: o nome da máquina (neste caso informado pelo IP do localhost), um número de porta e um nome fantasia (neste exemplo, de forma original, o serviço se chama Fibonacci. 

(3) Uma satisfação para os incrédulos.

(4) Se ocorreu tudo bem, o serviço ficará ativo e não retornará ao prompt. 

*/
