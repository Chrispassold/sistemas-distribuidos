package src.core;

public class BullyAlgorithm {
    private Routines routines;
    private ProcessContainer processContainer;

    public static void main(String[] args) {
        new BullyAlgorithm();
    }

    public BullyAlgorithm() {
        routines = new Routines();
        processContainer = new ProcessContainer();

        routines.startNewRoutine(3, processContainer::requestToCoordinator);
        routines.startNewRoutine(5, processContainer::createProcess, true);
        routines.startNewRoutine(10, processContainer::killRandomProcess);
        routines.startNewRoutine(15, processContainer::killCoordinator);
    }


}
