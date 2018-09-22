import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

class Client {

    private Time time;

    private static String[] machines = new String[]{"localhost"};

    private Map<String, Long> timeDiff = new HashMap<>(machines.length);

    Client() throws RemoteException, NotBoundException {
        if (machines.length == 0) {
            System.out.println("nenhum host cadastrado");
            return;
        }

        time = new Time();
        System.out.println("CLIENT HOUR: " + time);

        this.setup();
        this.updateTimes();

    }

    private void setup() throws RemoteException, NotBoundException {
        for (String machine : machines) {

            BerkleyServer server = Server.getServer(machine);

            Time serverTime = server.getTime();
            if (serverTime != null) {
                long between = this.time.secondsBetween(serverTime);
                timeDiff.put(machine, between);
            }
        }
    }

    private long calculateAvg() {

        long soma = 0;

        for (Map.Entry<String, Long> stringLongEntry : timeDiff.entrySet()) {
            soma += stringLongEntry.getValue();
        }

        return soma / machines.length + 1;
    }

    private void updateTimes() throws RemoteException, NotBoundException {
        final long seconds = calculateAvg();

        this.time.updateTime(seconds);
        System.out.println("UPDATED: " + time);

        for (String machine : machines) {
            final BerkleyServer server = Server.getServer(machine);
            final Long currentMachineDiff = timeDiff.get(machine);
            server.updateTime((currentMachineDiff * -1) + seconds);
        }
    }

}
