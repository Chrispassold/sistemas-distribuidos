package src.core;

import java.util.Objects;

public class Process implements Comparable<Process> {
    private final int id;
    private boolean active = true;

    public Process(int id) {
        this.id = id;
    }

    public void inactive() {
        active = false;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized boolean isCoordinator() {
        Process coordinator = ProcessContainer.getCoordinator();
        return coordinator != null && coordinator.getId() == this.getId();
    }

    private synchronized boolean receiveCoordinatorMessage(String message) {

        if (isCoordinator() && isActive()) {
            ConsoleUtil.printPurple("Message received: %s", message);

            return true;
        }

        return false;
    }

    public boolean sendMessageToCoordinator() {
        if (isCoordinator()) {
            return true;
        }

        Process coordinator = ProcessContainer.getCoordinator();

        if (coordinator != null) {
            ConsoleUtil.printPurple("Sending message to coordinator...");
            return coordinator.receiveCoordinatorMessage("It is process " + getId() + ", cambio!");
        }

        return false;
    }

    @Override
    public int compareTo(Process o) {
        return this.getId() - o.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return id == process.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                '}';
    }
}
