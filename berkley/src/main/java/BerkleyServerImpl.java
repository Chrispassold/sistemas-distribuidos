import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.Random;

public class BerkleyServerImpl extends UnicastRemoteObject implements BerkleyServer {

    private LocalTime hour;

    BerkleyServerImpl() throws RemoteException {

        Random random = new Random();
        hour = LocalTime.of(random.nextInt(23), random.nextInt(59));

        System.out.println("SERVER HOUR: " + hour);
    }

    @Override
    public LocalTime getTime() throws RemoteException {
        return hour;
    }

    @Override
    public void updateTime(long seconds) throws RemoteException {
        hour = hour.plusSeconds(seconds);
        System.out.println("UPDATED: " + hour);
    }


}
