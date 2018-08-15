package src.core.actions;

import src.core.BullyAlgorithm;

public class KillProcess extends AbstractAction {

    public KillProcess(long seconds, BullyAlgorithm bullyAlgorithm) {
        super(seconds, bullyAlgorithm);
        startAfterSleep();
    }

    @Override
    public synchronized void execute() {
        this.bullyAlgorithm.killRandomProcess();
    }
}
