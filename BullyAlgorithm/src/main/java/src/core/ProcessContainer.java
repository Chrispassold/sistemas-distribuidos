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
        System.out.println("[process] created " + id);
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

    public void requestToCoordinator() {
        Process randomProcess = getRandomProcess();
        if (randomProcess != null) {
            boolean ok = randomProcess.sendMessageToCoordinator();

            if (!ok) {
                Integer higherKey = processes.higherKey(randomProcess.getId());

                do {
                    /*
                    Exception in thread "Thread-0" java.lang.NullPointerException
	at src.core.ProcessContainer.requestToCoordinator(ProcessContainer.java:59)
	at src.core.Routines.lambda$startNewRoutine$0(Routines.java:28)
	at java.lang.Thread.run(Thread.java:748)
                    * */
                    if (higherKey == randomProcess.getId()) {
                        coordinator = randomProcess;
                    } else {
                        coordinator = processes.get(higherKey);
                    }

                    higherKey--;
                } while (!coordinator.isActive());

                System.out.println("[COORDINATOR] The new coordinator is process " + coordinator.getId());
            }
        }
    }

}
