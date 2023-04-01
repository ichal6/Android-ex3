package pl.lechowicz.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.widget.Toast
import java.util.*


class MyBoundService: Service() {
    private val binder = MyBinder()
    private lateinit var handlerThread: MyHandlerThread
    private var counter = 0
    private var timer: Timer? = null
    private var activity: MainActivity? = null

    inner class MyBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }

    override fun onCreate() {
        super.onCreate()
        handlerThread = MyHandlerThread()
        handlerThread.start()
    }

    override fun onBind(intent: Intent?): IBinder {
        Toast.makeText(this, "Your bound service has been started", Toast.LENGTH_SHORT).show()
        startTimer()
        return binder
    }

    fun getCounter(): Int {
        return counter
    }

    fun incrementCounter() {
        counter++
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Your bound service has been stopped", Toast.LENGTH_SHORT).show()
        stopTimer()
        handlerThread.quit()
    }

    private fun startTimer() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity?.showToast("Your bound service is still working")
                incrementCounter()
            }
        }, 0, 5000) // 5000ms delay before showing the toast again
    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    fun setActivity(activity: MainActivity) {
        this.activity = activity
    }

}

class MyHandlerThread : HandlerThread("MyHandlerThread") {
    private lateinit var serviceHandler: Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        serviceHandler = Handler(looper)
    }
}
