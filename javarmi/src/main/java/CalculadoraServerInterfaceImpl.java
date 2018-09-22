import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraServerInterfaceImpl extends UnicastRemoteObject implements CalculadoraServerInterface {
    CalculadoraServerInterfaceImpl() throws RemoteException {
    }

    @Override
    public int somar(int a, int b) {
        System.out.println("Somar: A"+a + "B "+ b);
        return a + b;
    }
}
