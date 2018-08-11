import actions.KillCoordinator
import actions.KillRandomProcess
import actions.NewProcess
import actions.RequestCoordinator

class BullyAlgorithm {

    var processList: LinkedHashMap<Long, Process> = linkedMapOf()

    private val newProcess: NewProcess = NewProcess(30)
    private val requestCoordinator: RequestCoordinator = RequestCoordinator(25)
    private val killCoordinator: KillCoordinator = KillCoordinator(100)
    private val killRandomProcess: KillRandomProcess = KillRandomProcess(80)

    init {
        killCoordinator.start()
        killRandomProcess.start()
        newProcess.start()
        requestCoordinator.start()
    }

}