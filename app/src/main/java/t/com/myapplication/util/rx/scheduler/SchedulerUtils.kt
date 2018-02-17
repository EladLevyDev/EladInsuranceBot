package t.com.myapplication.util.rx.scheduler


object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}
