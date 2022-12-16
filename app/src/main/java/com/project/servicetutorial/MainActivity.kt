package com.project.servicetutorial

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.project.servicetutorial.databinding.ActivityMainBinding
import com.project.servicetutorial.services.LocalAppService
import com.project.servicetutorial.utils.UtilsClass.Companion.getRandomColor

class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var localAppService: LocalAppService
    private var bound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as LocalAppService.LocalBinder
            localAppService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.btnStartService.setOnClickListener {
            if (bound) {
                val intent = Intent(this, LocalAppService::class.java)
                startService(intent)
                Log.d(getString(R.string.start), "MediaPlayer started playing -> -> ")
            }
        }

        binding.btnStopService.setOnClickListener {
            if (bound) {
                val intent = Intent(this, LocalAppService::class.java)
                stopService(intent)
                Log.d(getString(R.string.stop), "MediaPlayer Stopped")
            }
        }

        binding.root.setBackgroundColor(getRandomColor())
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        Intent(this, LocalAppService::class.java)
            .also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        bound = false
    }
}