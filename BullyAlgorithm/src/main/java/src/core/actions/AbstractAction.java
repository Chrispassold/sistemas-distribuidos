package src.core.actions;

import src.core.BullyAlgorithm;

import java.util.concurrent.TimeUnit;

public abstract class AbstractAction extends Thread {

    final protected long interval;
    protected final BullyAlgorithm bullyAlgorithm;

    private boolean startAfterSleep = false;

    public AbstractAction(long seconds, BullyAlgorithm bullyAlgorithm) {
        interval = TimeUnit.SECONDS.toMillis(seconds);
        this.bullyAlgorithm = bullyAlgorithm;
    }

    @Override
    public void run() {
        try {
            while (true) {

                if (startAfterSleep) {
                    sleep(this.interval);
                    execute();
                    this.bullyAlgorithm.printAvaiableProcess();
                } else {
                    execute();
                    this.bullyAlgorithm.printAvaiableProcess();
                    sleep(this.interval);
                }
            }
        } catch (InterruptedException ignore) {
        }
    }

    public void startAfterSleep() {
        startAfterSleep = true;
    }

    public abstract void execute() throws InterruptedException;

}
