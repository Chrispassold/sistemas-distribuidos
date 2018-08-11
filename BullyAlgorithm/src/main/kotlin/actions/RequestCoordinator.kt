package actions

class RequestCoordinator(_duration: Long) : AbstractAction(_duration) {
    override fun execute() {
        println("requesting to coordinator")
    }

}