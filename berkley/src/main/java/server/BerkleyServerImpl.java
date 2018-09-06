package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.Random;

public class BerkleyServerImpl extends UnicastRemoteObject implements BerkleyServer {

    private LocalTime hour;

    protected BerkleyServerImpl() throws RemoteException {

        Random random = new Random();
        hour = LocalTime.of(random.nextInt(23), random.nextInt(59));
    }

    @Override
    public LocalTime getHour() {
        return hour;
    }

    @Override
    public void setHour(LocalTime hour) {
        this.hour = hour;
        System.out.println(hour);
    }
}
