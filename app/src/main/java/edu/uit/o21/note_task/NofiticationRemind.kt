package edu.uit.o21.note_task

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit
import kotlin.random.Random
//notification for the application
class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
//manual input for notification
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
//Worker to run push notification
    private suspend fun showRandomNotification() {
        val randomIndex = Random.nextInt(notificationMessages.size)
        val message = notificationMessages[randomIndex]
        NotificationUtil.showNotification(applicationContext, "Note Task Reminder", message)
    }

    companion object {
        const val WORK_TAG = "ReminderWorker"

        fun scheduleReminder(context: Context, intervalMinutes: Long) {
            val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
                intervalMinutes, TimeUnit.MINUTES
            ).addTag(WORK_TAG).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
        }
    }
}
