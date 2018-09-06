package server;

import java.rmi.Remote;
import java.time.LocalTime;

public interface BerkleyServer extends Remote {
    LocalTime getHour();

    void setHour(LocalTime hour);
}
