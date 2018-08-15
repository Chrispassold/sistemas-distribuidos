package src.core.actions;

import src.core.BullyAlgorithm;

public class NewProcess extends AbstractAction {

    public NewProcess(long seconds, BullyAlgorithm bullyAlgorithm) {
        super(seconds, bullyAlgorithm);
    }

    @Override
    public synchronized void execute() {
        this.bullyAlgorithm.createProcess();
    }
}
