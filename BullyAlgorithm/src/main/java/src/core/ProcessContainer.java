package src.core;

import java.util.Random;
import java.util.TreeMap;

public class ProcessContainer {
    private static int latestId = 0;
    private TreeMap<Integer, Process> processes;
    private static Process coordinator;

    public ProcessContainer() {
        processes = new TreeMap<>();
    }

    public static Process getCoordinator() {
        return coordinator;
    }

    public void createProcess() {
        int id = latestId++;
        Process process = new Process(id);

        if (coordinator == null) {
            coordinator = process;
        }

        processes.put(id, process);
        Util.print("[process] created " + id);
    }

    protected Process getRandomProcess() {
        Random generator = new Random();
        while (true) {
            int random = generator.nextInt(latestId);
            if ((coordinator == null || random != coordinator.getId()) && processes.containsKey(random)) {
                return processes.get(random);
            }
        }
    }

    public void killRandomProcess() {
        Process randomProcess = getRandomProcess();
        if (randomProcess != null) randomProcess.inactive();
    }

    public void killCoordinator() {
        if (coordinator != null) coordinator.inactive();
    }

    public synchronized void requestToCoordinator() {
        Process randomProcess = getRandomProcess();
        if (randomProcess != null) {
            boolean ok = randomProcess.sendMessageToCoordinator();

            if (!ok) {
                int higherKey = processes.higherKey(randomProcess.getId());
                while(coordinator != null && !coordinator.isActive()){
                    if (higherKey == randomProcess.getId()) {
                        coordinator = randomProcess;
                    } else {
                        coordinator = processes.get(higherKey);
                    }

                    higherKey--;
                }

                if (coordinator != null) {
                    Util.print("[COORDINATOR] The new coordinator is process " + String.valueOf(coordinator), true);
                } else {
                    Util.print("[COORDINATOR] Any one can be a coordinator", true);
                }
            }
        }
    }

}
