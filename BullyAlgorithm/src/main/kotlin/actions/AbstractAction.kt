package actions

import java.util.concurrent.TimeUnit

abstract class AbstractAction(private val _duration: Long) : Thread() {

    override fun run() {
        try {
            while (true){
                execute()
                sleep(TimeUnit.SECONDS.toMillis(_duration))
            }
        }catch (exception: InterruptedException){}
    }

    abstract fun execute()
}