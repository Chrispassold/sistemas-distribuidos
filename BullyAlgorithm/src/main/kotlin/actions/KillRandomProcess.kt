package actions

class KillRandomProcess(_duration: Long) : AbstractAction(_duration) {

    override fun execute() {
        println("killing coordinator")
    }
}