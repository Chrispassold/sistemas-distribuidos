import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculadoraServerInterface extends Remote {
    // método público que recebe dois valores inteiros e// retorna sua soma

    int somar(int a, int b) throws RemoteException;
}
