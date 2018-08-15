package src.core.actions;

import src.core.BullyAlgorithm;

public class ProcessRequest extends AbstractAction {

    public ProcessRequest(long seconds, BullyAlgorithm bullyAlgorithm) {
        super(seconds, bullyAlgorithm);
    }

    @Override
    public synchronized void execute() {
    }
}
