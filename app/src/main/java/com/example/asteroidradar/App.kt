package com.example.asteroidradar

import android.app.Application
import androidx.work.*
import com.example.asteroidradar.worker.MyWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class App: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayInit()
    }

    private fun delayInit() {
        applicationScope.launch {
            setupWork()
        }
    }

    private fun setupWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val myRequest: WorkRequest = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.DAYS).setConstraints(constraints).build()

        WorkManager.getInstance(applicationContext).enqueue(myRequest)
    }
}