package edu.uit.o21.note_task

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    private val notificationMessages = listOf(
        "Do you have any tasks today?",
        "Remember to note down important things!",
        "Don't forget to check your tasks.",
        "Do you need to add anything to your notes?"
    )

    override suspend fun doWork(): Result {
        showRandomNotification()
        return Result.success()
    }

    private suspend fun showRandomNotification() {
        val randomIndex = Random.nextInt(notificationMessages.size)
        val message = notificationMessages[randomIndex]
        NotificationUtil.showNotification(applicationContext, "Note Task Reminder", message)
    }

    companion object {
        const val WORK_TAG = "ReminderWorker"

        fun scheduleReminder(context: Context, intervalMinutes: Long) {
            val workRequest = androidx.work.PeriodicWorkRequestBuilder<ReminderWorker>(
                intervalMinutes, TimeUnit.MINUTES
            ).addTag(WORK_TAG).build()

            androidx.work.WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}
