package src.core;

import src.core.actions.KillCoordinator;
import src.core.actions.KillProcess;
import src.core.actions.NewProcess;
import src.core.actions.ProcessRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BullyAlgorithm {

    final private Thread newProcess, processRequest, killProcess, killCoordinator;
    private int latestId = 0;
    private Map<Integer, Process> processes;

    public BullyAlgorithm() {
        processes = new HashMap<>();

        processRequest = new ProcessRequest(25, this);
        newProcess = new NewProcess(5, this);
        killProcess = new KillProcess(10, this);
        killCoordinator = new KillCoordinator(100, this);

        processRequest.start();
        newProcess.start();
        killProcess.start();
        killCoordinator.start();
    }

    public synchronized void createProcess() {
        int id = latestId++;
        Process process = new Process(id);
        processes.put(id, process);
    }

    public synchronized void killRandomProcess() {
        Random generator = new Random();
        while (!processes.isEmpty()) {
            int random = generator.nextInt(latestId);
            if (processes.containsKey(random)) {
                processes.remove(random);
                System.out.println("[process] kill " + random);
                return;
            }
        }
    }

    public synchronized void printAvaiableProcess() {
        System.out.println(Arrays.toString(processes.keySet().toArray()));
    }

}
