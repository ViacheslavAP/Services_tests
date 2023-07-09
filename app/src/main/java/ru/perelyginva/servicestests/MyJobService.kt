package ru.perelyginva.servicestests

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobService : JobService() {

    companion object {
        const val JOB_ID = 11
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartJob")
        Log.d("MyJobService", "onStartJob")
        coroutineScope.launch {
            for (page in 0 until 10) {
                for (i in 0 until 5) {
                    delay(1000)
                    log("Timer $i  $page")
                }
            }
            jobFinished(p0, true)
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onCreate() {
        super.onCreate()
        log("Start onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        coroutineScope.cancel()
    }

    private fun log(message: String) {
        Log.d("SERVICE_LOG", "MyJobService : $message")
    }

}