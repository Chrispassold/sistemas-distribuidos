import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BerkleyServer extends Remote {
    Time getTime() throws RemoteException;
    void updateTime(long seconds) throws RemoteException;
}
