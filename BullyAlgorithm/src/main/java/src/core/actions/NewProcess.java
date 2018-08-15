package src.core.actions;

import src.core.Process;

import java.util.concurrent.BlockingQueue;

public class NewProcess extends AbstractAction {

    private int lastId = 0;

    public NewProcess(long seconds, BlockingQueue<Process> processes) {
        super(seconds, processes);
    }

    @Override
    public synchronized void execute() throws InterruptedException {
        Process process = new Process(++lastId);
        putProcess(process);
        System.out.println("New process > " + lastId);
    }
}
