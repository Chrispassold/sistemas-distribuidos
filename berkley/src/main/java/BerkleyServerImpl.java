import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BerkleyServerImpl extends UnicastRemoteObject implements BerkleyServer {

    private Time time;

    BerkleyServerImpl() throws RemoteException {
        time = new Time();
        System.out.println("SERVER HOUR: " + time);
    }

    @Override
    public Time getTime() throws RemoteException {
        return time;
    }

    @Override
    public void updateTime(long seconds) throws RemoteException {
        time.updateTime(seconds);
        System.out.println("UPDATED: " + time);
    }


}
