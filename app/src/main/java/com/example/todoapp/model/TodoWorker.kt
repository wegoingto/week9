package com.example.todoapp.model

import android.app.Activity
import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.todoapp.util.NotificationHelper
import java.util.concurrent.TimeUnit

class TodoWorker(val context: Context, val params:WorkerParameters): Worker(context,params) {
    override fun doWork(): Result {
        val notif = NotificationHelper(context)
        notif.createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString()
        )
        return Result.success()

    }

}