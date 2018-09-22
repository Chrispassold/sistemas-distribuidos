
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            CalculadoraServerInterface c = (CalculadoraServerInterface) registry.lookup(MainServer.REGISTER_CALC);
            System.out.println("O objeto servidor " + c + " foi encontrado com sucesso.\n");
            System.out.println("A soma de 2 + 5 é: " + c.somar(2, 5));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
