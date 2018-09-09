import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Time implements Serializable {

    private LocalTime time;

    Time() {
        Random random = new Random();
        time = LocalTime.of(random.nextInt(23), random.nextInt(59));
    }

    private LocalTime getTime() {
        return time;
    }

    void updateTime(long seconds) {
        time = time.plusSeconds(seconds);
    }

    long secondsBetween(Time time) {
        return ChronoUnit.SECONDS.between(this.time, time.getTime());
    }

    @Override
    public String toString() {
        return time.toString();
    }
}
