package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {
    public static final String REGISTER = "BERKLEY";

    public static void main(String[] args) {

        try {
            BerkleyServer sdrmi = new BerkleyServerImpl();
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(REGISTER, sdrmi);

            System.out.println("Servidor Berkley " + sdrmi + " registrado e pronto para aceitar solicitações.");
        } catch (Exception ex) {
            System.out.println("Houve um erro: " + ex.getMessage());
        }
    }
}
