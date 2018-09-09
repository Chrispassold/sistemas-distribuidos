import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.Random;

public class BerkleyServerImpl extends UnicastRemoteObject implements BerkleyServer {

    private LocalTime hour;

    BerkleyServerImpl() throws RemoteException {

        Random random = new Random();
        hour = LocalTime.of(random.nextInt(23), random.nextInt(59));
    }

    @Override
    public LocalTime getHour()throws RemoteException {
        return hour;
    }

    @Override
    public void setHour(LocalTime hour) throws RemoteException {
        this.hour = hour;
        System.out.println(hour);
    }
}
