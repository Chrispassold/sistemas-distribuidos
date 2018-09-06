package client;

import server.BerkleyServer;
import server.MainServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class MainClient {

    private static String[] machines = new String[]{};

    private static Map<String, LocalTime> hours = new HashMap<>(machines.length);

    public static void main(String[] args) {
        try {
            bringHours();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void bringHours() throws RemoteException, NotBoundException {
        if (machines.length > 0) {
            for (String machine : machines) {
                Registry registry = LocateRegistry.getRegistry(machine);
                BerkleyServer server = (BerkleyServer) registry.lookup(MainServer.REGISTER);
                LocalTime hour = server.getHour();
                if (hour != null) hours.put(machine, hour);
            }
        }
    }
}
