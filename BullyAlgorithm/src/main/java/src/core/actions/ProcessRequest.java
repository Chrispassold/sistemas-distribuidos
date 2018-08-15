package src.core.actions;

import src.core.Process;

import java.util.concurrent.BlockingQueue;

public class ProcessRequest extends AbstractAction {

    public ProcessRequest(long seconds, BlockingQueue<Process> processes) {
        super(seconds, processes);
    }

    @Override
    public synchronized void execute() {
        System.out.println("New process request");
    }
}
