package com.example.boardcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.boardcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()
        with(binding){
            txtUsername.text = prefManager.getUsername()
//            btnLogout.setOnClickListener {
//                prefManager.saveUsername("")
//                checkLoginStatus()
//            }
            btnClear.setOnClickListener {
                prefManager.clear()
                checkLoginStatus()            }
        }
    }

    private fun checkLoginStatus(){
         val channelId = "TEST_NOTIF"
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val username = prefManager.getUsername()
        if (prefManager.getUsername().isEmpty()){
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_IMMUTABLE
            } else {
                0
            }

            val intentToLogin = Intent(this@MainActivity, LoginActivtiy::class.java).putExtra("Message", "Baca Selengkapnya")

            val pendingIntent = PendingIntent.getActivity(this, 0, intentToLogin, flag)


            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notif)
                .setContentTitle("Keluar")
                .setContentText("Anda berhasil keluar") // Isi pesan bebas
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
//                .addAction(0, "Buka", pendingIntent)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notifChannel = NotificationChannel(
                    channelId, // Id channel
                    "Keluar", // Nama channel notifikasi
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                with(notifManager) {
                    createNotificationChannel(notifChannel)
                    notify(0, builder.build())
                }
            }
            else {
                notifManager.notify(0, builder.build())
            }
//            startActivity(Intent(this@MainActivity, LoginActivtiy::class.java))
            finish()
        }
    }
}