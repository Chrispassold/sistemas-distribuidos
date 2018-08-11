package actions

class KillCoordinator(_duration: Long) : AbstractAction(_duration) {

    override fun execute() {
        println("killing coordinator")
    }
}