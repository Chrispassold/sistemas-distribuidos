import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;

public interface BerkleyServer extends Remote {
    LocalTime getHour() throws RemoteException;
    void setHour(LocalTime hour) throws RemoteException;
}
