import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class Client {

    private LocalTime hour;

    private String[] machines = new String[]{"localhost"};

    private Map<String, Long> hoursDiff = new HashMap<>(machines.length);

    Client() throws RemoteException, NotBoundException {
        Random random = new Random();
        hour = LocalTime.of(random.nextInt(23), random.nextInt(59));

        System.out.println("CLIENT HOUR: " + hour);

        initialize();
    }

    private void initialize() throws RemoteException, NotBoundException {
        if (machines.length == 0) {
            System.out.println("nenhum host cadastrado");
            return;
        }

        for (String machine : machines) {

            BerkleyServer server = Server.getServer(machine);

            LocalTime hour = server.getTime();
            if (hour != null) {
                long between = ChronoUnit.SECONDS.between(this.hour, hour);
                hoursDiff.put(machine, between);
            }
        }

        final long avg = calculateAvg();
        hour = this.hour.plusSeconds(avg);
        System.out.println("UPDATED: " + hour);
        for (String machine : machines) {
            final BerkleyServer server = Server.getServer(machine);
            final Long currentMachineDiff = hoursDiff.get(machine);
            server.updateTime((currentMachineDiff * -1) + avg);
        }
    }

    private long calculateAvg() {

        long soma = 0;

        for (Map.Entry<String, Long> stringLongEntry : hoursDiff.entrySet()) {
            soma += stringLongEntry.getValue();
        }

        return soma / machines.length + 1;
    }
}
