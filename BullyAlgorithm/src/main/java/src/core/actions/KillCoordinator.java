package src.core.actions;

import src.core.BullyAlgorithm;

public class KillCoordinator extends AbstractAction {

    public KillCoordinator(long seconds, BullyAlgorithm bullyAlgorithm) {
        super(seconds, bullyAlgorithm);
        startAfterSleep();
    }

    @Override
    public synchronized void execute() {
    }
}
