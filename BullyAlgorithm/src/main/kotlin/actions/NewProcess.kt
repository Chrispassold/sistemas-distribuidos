package actions

class NewProcess(_duration: Long) : AbstractAction(_duration) {
    override fun execute() {
        println("creating new process")
    }
}