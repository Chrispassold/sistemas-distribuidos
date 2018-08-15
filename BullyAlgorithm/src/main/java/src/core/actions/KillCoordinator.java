package src.core.actions;

import src.core.Process;

import java.util.concurrent.BlockingQueue;

public class KillCoordinator extends AbstractAction {

    public KillCoordinator(long seconds, BlockingQueue<Process> processes) {
        super(seconds, processes);
    }

    @Override
    public synchronized void execute() {
        System.out.println("Coordinator is dead");
    }
}
