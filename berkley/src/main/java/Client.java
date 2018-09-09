import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Client {

    private String[] machines = new String[]{"localhost"};

    private Map<String, LocalTime> hours = new HashMap<>(machines.length);

    Client() throws RemoteException, NotBoundException {
        bringHours();
    }

    private void bringHours() throws RemoteException, NotBoundException {
        if (machines.length > 0) {
            for (String machine : machines) {
                Registry registry = LocateRegistry.getRegistry(machine);
                BerkleyServer server = (BerkleyServer) registry.lookup(Server.REGISTER);
                LocalTime hour = server.getHour();
                if (hour != null){
                    System.out.println(hour.toString());
                    hours.put(machine, hour);
                }
            }
        }
    }
}
