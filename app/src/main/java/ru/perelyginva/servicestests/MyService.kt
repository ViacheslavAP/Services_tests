package ru.perelyginva.servicestests

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService: Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main )

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        log("Start onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val start = intent?.getIntExtra(EXTRA_START, 0)?: 0
        log("onStartCommand")
        coroutineScope.launch {
            for (i in start until start +  100){
                delay(1000)
                log("Timer $i")
            }
        }

        return START_REDELIVER_INTENT

    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        coroutineScope.cancel( )
    }

    private fun log(message: String){
        Log.d("SERVICE_LOG", "MyService: $message")
    }

    companion object{
        private const val EXTRA_START = "start"
        fun newIntent(context: Context, start: Int): Intent{
            return Intent(context, MyService::class.java ).apply {
                putExtra(EXTRA_START, start)
            }
        }
    }
}