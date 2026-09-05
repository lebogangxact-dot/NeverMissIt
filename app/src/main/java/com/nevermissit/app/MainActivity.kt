package com.nevermissit.app

import android.app.*
import android.content.*
import android.os.Bundle
import android.provider.Settings
import android.net.Uri
import android.widget.*
import java.util.Calendar

class MainActivity : Activity() {
    private lateinit var reminder: EditText
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private var year=0; private var month=0; private var day=0; private var hour=0; private var minute=0
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); createChannel(); showUi() }
    private fun showUi() {
        val root=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(40,48,40,32) }
        val title=TextView(this).apply { text="Never Miss It"; textSize=30f; setPadding(0,0,0,12) }
        val sub=TextView(this).apply { text="Set a reminder and never miss it."; textSize=16f; setPadding(0,0,0,24) }
        reminder=EditText(this).apply { hint="What do you want to remember?"; minLines=2 }
        dateButton=Button(this).apply { text="Choose date"; setOnClickListener { pickDate() } }
        timeButton=Button(this).apply { text="Choose time"; setOnClickListener { pickTime() } }
        val set=Button(this).apply { text="SET REMINDER"; setOnClickListener { schedule() } }
        root.addView(title); root.addView(sub); root.addView(reminder); root.addView(dateButton); root.addView(timeButton); root.addView(set)
        setContentView(root)
    }
    private fun pickDate(){ val c=Calendar.getInstance(); DatePickerDialog(this,{_,y,m,d->year=y;month=m;day=d;dateButton.text="Date: %02d/%02d/%04d".format(d,m+1,y)},c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show() }
    private fun pickTime(){ val c=Calendar.getInstance(); TimePickerDialog(this,{_,h,m->hour=h;minute=m;timeButton.text="Time: %02d:%02d".format(h,m)},c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true).show() }
    private fun schedule(){ val text=reminder.text.toString().trim(); if(text.isEmpty()){ reminder.error="Enter a reminder"; return }; if(year==0){Toast.makeText(this,"Choose a date",Toast.LENGTH_SHORT).show();return}; val now=Calendar.getInstance(); val cal=Calendar.getInstance().apply{set(year,month,day,hour,minute,0);set(Calendar.MILLISECOND,0)}; if(cal.timeInMillis<=now.timeInMillis){Toast.makeText(this,"Choose a future time",Toast.LENGTH_SHORT).show();return}; val am=getSystemService(ALARM_SERVICE) as AlarmManager; if(!am.canScheduleExactAlarms()){startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:$packageName"))); Toast.makeText(this,"Allow alarms & reminders, then tap SET REMINDER again",Toast.LENGTH_LONG).show();return}; val i=Intent(this,ReminderReceiver::class.java).apply{putExtra("text",text)}; val pi=PendingIntent.getBroadcast(this,cal.timeInMillis.toInt(),i,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE); am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.timeInMillis,pi); Toast.makeText(this,"Reminder set",Toast.LENGTH_LONG).show() }
    private fun createChannel(){ if(android.os.Build.VERSION.SDK_INT>=26){ val nm=getSystemService(NOTIFICATION_SERVICE) as NotificationManager; nm.createNotificationChannel(NotificationChannel("reminders",getString(R.string.notification_channel_name),NotificationManager.IMPORTANCE_HIGH).apply{description=getString(R.string.notification_channel_description)}) } }
}
