import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;

public interface BerkleyServer extends Remote {
    LocalTime getTime() throws RemoteException;
    void updateTime(long seconds) throws RemoteException;
}
