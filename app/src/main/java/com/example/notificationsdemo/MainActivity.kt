package com.example.notificationsdemo

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ContentInfoCompat
import com.example.notificationsdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var notificationManager: NotificationManagerCompat
    val notificationChannelId = "bitcode_entertainment_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationManager = NotificationManagerCompat.from(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simpleNotification()
        snackBarNotification()
        bigPictureNotification()
        inboxStyleNotification()
        actionTextStyleNotification()
        createNotificationChannels()

    }

    fun simpleNotification(){
        binding.btnSimpleNotification.setOnClickListener {
            var notificationBuilder = NotificationCompat.Builder(this,notificationChannelId)
            notificationBuilder.setContentTitle("Bitcode Technologies")
            notificationBuilder.setContentText("Welcome To Bitcode!")

            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
            var largeIcon = BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher)
            notificationBuilder.setLargeIcon(largeIcon)
            notificationBuilder.color = Color.RED
            notificationBuilder.setNumber(3)
            notificationBuilder.setVibrate(
                LongArray(10,{index -> 400})
            )

            notificationBuilder.setLights(Color.RED,500,400)
            notificationBuilder.setAutoCancel(true)
            notificationBuilder.setOngoing(true)
            notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_SECRET)

            var actionIntent = Intent(this,SecondActivity::class.java)
            var actionPendingIntent = PendingIntent.getActivity(
                this,
                1,
                actionIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            notificationBuilder.setContentIntent(actionPendingIntent)

            var notification = notificationBuilder.build()
            notificationManager.notify(101,notification)
        }
    }

    fun actionTextStyleNotification(){
        binding.btnActionTextStyleNotification.setOnClickListener {
            var builder = NotificationCompat.Builder(this,notificationChannelId)
            builder.setContentTitle("Android Admissions Open")
            builder.setContentText("Contact Bitcode for Admissions")
            builder.setSmallIcon(R.mipmap.ic_launcher)

            var actionIntent = Intent(this,SecondActivity::class.java)
            var actionPendingIntent = PendingIntent.getActivity(
                this,
                1,
                actionIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            builder.setAutoCancel(true)

            var actionRegister = NotificationCompat.Action(
                R.mipmap.ic_launcher,
                "Admissions Open",
                actionPendingIntent
            )

            var actionIntentForShare = Intent(this,HomeActivity::class.java)
            var actionPendingIntentShare = PendingIntent.getActivity(
                this,
                1,
                actionIntentForShare,
                PendingIntent.FLAG_IMMUTABLE
            )

            builder.addAction(actionRegister)
            builder.addAction(R.mipmap.ic_launcher,"Share",actionPendingIntentShare)
            builder.addAction(R.mipmap.ic_launcher,"Not Useful",actionPendingIntent)

            notificationManager.notify(104,builder.build())
        }
    }

    fun inboxStyleNotification(){
        binding.btnInboxStyleNotification.setOnClickListener {
            var builder = NotificationCompat.Builder(this,notificationChannelId)
            builder.setContentTitle("Android October Batch Admissions Open")
            builder.setContentText("For Android Admissions contact Bitcode!")
            builder.setSmallIcon(R.mipmap.ic_launcher)


            var inboxStyle = NotificationCompat.InboxStyle()
            inboxStyle.addLine("Android By Vishal Sir, Aishwarya Mam")
            inboxStyle.addLine("iOS by Aishwarya Mam")
            inboxStyle.addLine("Java by Akanksha Mam")
            inboxStyle.addLine("Web/React by Sonal & Shruti Mam")

            builder.setStyle(inboxStyle)
            notificationManager.notify(103,builder.build())
        }
    }

    fun bigPictureNotification(){
        binding.btnBigPictureNotification.setOnClickListener {
            var builder = NotificationCompat.Builder(this,notificationChannelId)
            builder.setContentTitle("Admissions Started for Next Android..")
            builder.setContentText("Contact Bitcode for Android!")
            builder.setSmallIcon(R.mipmap.ic_launcher)

            var bigPictureStyle = NotificationCompat.BigPictureStyle()
            bigPictureStyle.setBigContentTitle("Admissions Open..")
            bigPictureStyle.setSummaryText("Contact Mr. Sushil Raut For Admissions..")

            var bitmapImage = BitmapFactory.decodeResource(resources,R.drawable.nature)
            bigPictureStyle.bigPicture(bitmapImage)
            builder.setStyle(bigPictureStyle)
            notificationManager.notify(102,builder.build())
        }
    }

    fun snackBarNotification(){
        binding.btnSnackBarNotification.setOnClickListener {
            Snackbar.make(
                binding.root,
                "Downloading Completed",
                Snackbar.LENGTH_LONG
            )
                .setTextColor(Color.WHITE)
                .setAction(
                    "View"
                ) {
                    Log.e("tag", "View Action Taken")
                }
                .show()
        }
    }

    fun createNotificationChannels(){
        var channelEntertainmentBuilder = NotificationChannelCompat.Builder(
            "bitcode_entertainment_channel",NotificationManager.IMPORTANCE_DEFAULT
        )

        channelEntertainmentBuilder.setName("Bitcode Channel")
        channelEntertainmentBuilder.setDescription("Bitcode demos")
        channelEntertainmentBuilder.setShowBadge(true)

        notificationManager.createNotificationChannel(channelEntertainmentBuilder.build())
    }
}