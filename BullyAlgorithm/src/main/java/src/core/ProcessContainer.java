package src.core;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

public class ProcessContainer {
    private static int latestId = 0;
    private TreeMap<Integer, Process> processes;
    private static Process coordinator;

    private static boolean isElecting = false;

    public ProcessContainer() {
        processes = new TreeMap<>();
    }

    public static Process getCoordinator() {
        return coordinator;
    }

    public synchronized void setCoordinator(Process newCoordinator) {
        coordinator = newCoordinator;
    }

    public synchronized void createProcess() {
        int id = latestId++;
        Process process = new Process(id);

        if (getCoordinator() == null) {
            this.setCoordinator(process);
        }

        processes.put(id, process);
        ConsoleUtil.printGreen("Created a new process %s", process);
    }

    protected Process getRandomProcess() {
        if (processes.isEmpty()) return null;

        Random generator = new Random();
        while (true) {
            int random = generator.nextInt(latestId);
            if ((getCoordinator() == null || random != getCoordinator().getId()) && processes.containsKey(random)) {
                return processes.get(random);
            }
        }
    }

    public void killRandomProcess() {
        Process randomProcess = getRandomProcess();
        if (randomProcess != null) {
            randomProcess.inactive();
            ConsoleUtil.printRed("Killing %s", randomProcess);
        }
    }

    public void killCoordinator() {
        if (getCoordinator() != null) {
            getCoordinator().inactive();
            ConsoleUtil.printRed("Killing coordinator %s", getCoordinator());
        }
    }

    public void requestToCoordinator() {
        Process randomProcess = getRandomProcess();
        if (randomProcess != null) {
            boolean ok = randomProcess.sendMessageToCoordinator();

            if (ok || isElecting) return;

            this.startElection(randomProcess);
        }
    }

    private void startElection(Process whoStarts) {
        isElecting = true;
        ConsoleUtil.printBlue("Process %d started an election", whoStarts.getId());
        ConsoleUtil.printBlue("%s", Arrays.toString(processes.keySet().toArray()));
        int higherKey = processes.lastKey();
        Process newCoordinator = null;

        while (newCoordinator == null || !newCoordinator.isActive()) {
            if (processes.containsKey(higherKey)) {
                if (higherKey == whoStarts.getId()) {
                    newCoordinator = whoStarts;
                } else {
                    newCoordinator = processes.getOrDefault(higherKey, null);
                }
            }

            higherKey--;
        }

        this.setCoordinator(newCoordinator);
        ConsoleUtil.printBlue("The new coordinator is %s", getCoordinator());

        isElecting = false;
    }

}
