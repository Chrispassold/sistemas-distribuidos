package src.core;

public class Process {
    private int id;

    private boolean isCoordinator = false;

    private boolean isActive = true;

    public Process(int id) {
        this.id = id;
    }

    public void inactive() {
        isActive = false;
    }

    private void setAsCoordinator() {
        isCoordinator = true;
    }

}
