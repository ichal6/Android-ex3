package pl.lechowicz.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var myService: MyBoundService
    private var isServiceBound: Boolean = false
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            myService = myBinder.getService()
            myService.setActivity(this@MainActivity)
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goToListButton = findViewById<Button>(R.id.goToList)
        val getCounterButton = findViewById<Button>(R.id.getCounter)

        getCounterButton.setOnClickListener{
            getCounterFromService()
        }

        goToListButton.setOnClickListener{
            goToActivity()
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, MyBoundService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    private fun getCounterFromService() {
        if (isServiceBound) {
            Toast.makeText(this, "${myService.getCounter()}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToActivity() {
        val intent = Intent(this, NextActivity::class.java)
        startActivity(intent)
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
