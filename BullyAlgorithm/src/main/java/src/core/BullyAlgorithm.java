package src.core;

import src.core.actions.KillCoordinator;
import src.core.actions.KillProcess;
import src.core.actions.NewProcess;
import src.core.actions.ProcessRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BullyAlgorithm {

    final private Thread newProcess, processRequest, killProcess, killCoordinator;

    private boolean isElecting = false;

    private BlockingQueue<Process> processes;

    public BullyAlgorithm() {
        processes = new LinkedBlockingQueue<>();

        processRequest = new ProcessRequest(25, processes);
        newProcess = new NewProcess(30, processes);
        killProcess = new KillProcess(80, processes);
        killCoordinator = new KillCoordinator(100, processes);

        processRequest.start();
        newProcess.start();
        killProcess.start();
        killCoordinator.start();
    }

    private synchronized void startElecting(){
        isElecting = true;
    }

}
