package src.core.actions;

import src.core.Process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class AbstractAction extends Thread {

    final protected long interval;
    private BlockingQueue<Process> processes;

    public AbstractAction(long seconds, BlockingQueue<Process> processes) {
        interval = TimeUnit.SECONDS.toMillis(seconds);
        this.processes = processes;
    }

    @Override
    public void run() {
        try {
            while (true) {
                execute();
                sleep(this.interval);
            }
        } catch (InterruptedException ignore) {
        }
    }

    protected void putProcess(Process process) throws InterruptedException {
        processes.put(process);
    }

    public abstract void execute() throws InterruptedException;

}
