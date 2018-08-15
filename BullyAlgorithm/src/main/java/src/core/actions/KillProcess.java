package src.core.actions;

import src.core.Process;

import java.util.concurrent.BlockingQueue;

public class KillProcess extends AbstractAction {

    public KillProcess(long seconds, BlockingQueue<Process> processes) {
        super(seconds, processes);
    }

    @Override
    public synchronized void execute() {
        System.out.println("Process is dead");
    }
}
