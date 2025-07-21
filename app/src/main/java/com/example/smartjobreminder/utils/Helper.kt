package com.example.smartjobreminder.utils

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.smartjobreminder.data.db.JobEntity
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

fun scheduleJobReminder(context: Context, job: JobEntity) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val deadlineDate = LocalDate.parse(job.deadLine, formatter)

    val deadlineMillis = deadlineDate
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val delay = deadlineMillis - System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)

    if (delay > 0) {
        val data = Data.Builder()
            .putString("title", job.title)
            .putString("description", "Reminder: \"${job.title}\" at ${job.company} is due in 24 hours.")
            .build()

        val workRequest = OneTimeWorkRequestBuilder<JobReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
