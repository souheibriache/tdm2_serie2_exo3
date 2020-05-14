package com.example.exo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val ACTION_SERVICE = "dz.esi.demonotifsser"
    companion object{
        val context =this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intnt = Intent()

        intnt.setClass(this, BootDeviceReceiver::class.java)
        sendBroadcast(intnt)

        val intent = Intent(this, AdhanService::class.java)
        intent.action = ACTION_SERVICE
        startService(intent)

        stop_el_adhan.setOnClickListener {
            stopService(intent)
        }

    }
}
