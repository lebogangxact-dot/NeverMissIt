package com.nevermissit.app
import android.app.*
import android.content.*
import android.os.Build
class ReminderReceiver: BroadcastReceiver(){ override fun onReceive(context: Context,intent: Intent){ val text=intent.getStringExtra("text") ?: "Reminder"; val n=Notification.Builder(context,"reminders").setSmallIcon(com.nevermissit.app.R.drawable.ic_notification).setContentTitle("Never Miss It").setContentText(text).setAutoCancel(true).setPriority(Notification.PRIORITY_HIGH).build(); if(Build.VERSION.SDK_INT<33 || context.checkSelfPermission("android.permission.POST_NOTIFICATIONS")==0){ (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(System.currentTimeMillis().toInt(),n) } } }
