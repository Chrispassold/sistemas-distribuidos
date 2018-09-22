import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {

    static final String REGISTER_CALC = "CalculadoraServerInterfaceImpl";

    //EXECUTAR NA PASTA TEMP

    public static void main(String[] args) {

        try {
            CalculadoraServerInterface sdrmi = new CalculadoraServerInterfaceImpl();
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(REGISTER_CALC, sdrmi);

            System.out.println("Servidor Calculadora " + sdrmi + " registrado e pronto para aceitar solicitações.");
        } catch (Exception ex) {
            System.out.println("Houve um erro: " + ex.getMessage());
        }
    }
}
