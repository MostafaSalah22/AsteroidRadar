package com.example.asteroidradar.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.database.LocalDatabase
import com.example.asteroidradar.repository.Repository
import retrofit2.HttpException

class MyWorker(context: Context, workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val db = LocalDatabase.getInstance(applicationContext)
        val repo = db?.let { Repository(it) }

        return try {
            repo?.refresh()
            Result.success()
        }
        catch (exception: HttpException){
            Result.retry()
        }
    }
}