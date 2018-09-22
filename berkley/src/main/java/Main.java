import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        if (args.length == 0) {
            System.out.println("Informe 'server' ou 'client'");
            return;
        }

        switch (args[0]) {
            case "server": {
                new Server();
                break;
            }
            case "client": {
                new Client();
                break;
            }
            default:
                System.out.println("Informe 'server' ou 'client'");
        }
    }
}
