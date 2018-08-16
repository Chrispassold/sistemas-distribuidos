package src.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Routines {

    List<Thread> threadList;

    public Routines() {
        threadList = new ArrayList<>();
    }

    public void startNewRoutine(int sec, Runnable runnable) {
        startNewRoutine(sec, runnable, false);
    }

    public void startNewRoutine(int sec, Runnable runnable, boolean startImmediately) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (startImmediately) {
                        runnable.run();
                        Thread.sleep(TimeUnit.SECONDS.toMillis(sec));
                    } else {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(sec));
                        runnable.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();
        threadList.add(thread);
    }

}
