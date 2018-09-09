import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class Server {
    static final String REGISTER = "BERKLEY";

    Server() {
        try {
            BerkleyServer sdrmi = new BerkleyServerImpl();
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(REGISTER, sdrmi);

            System.out.println("Servidor Berkley " + sdrmi + " registrado e pronto para aceitar solicitações.");
        } catch (Exception ex) {
            System.out.println("Houve um erro: " + ex.getMessage());
        }
    }

    public static BerkleyServer getServer(String machine) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(machine);
        return (BerkleyServer) registry.lookup(Server.REGISTER);
    }

}
