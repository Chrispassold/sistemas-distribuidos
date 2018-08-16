package src.core;

public class BullyAlgorithm {
    private Routines routines;
    private ProcessContainer processContainer;

    public BullyAlgorithm() {
        routines = new Routines();
        processContainer = new ProcessContainer();

        routines.startNewRoutine(1, processContainer::requestToCoordinator, false);
        routines.startNewRoutine(3, processContainer::createProcess);
        routines.startNewRoutine(7, processContainer::killRandomProcess, false);
        routines.startNewRoutine(10, processContainer::killCoordinator, false);
    }


}
