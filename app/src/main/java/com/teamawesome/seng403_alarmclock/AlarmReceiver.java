package com.teamawesome.seng403_alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Eric Matteucci on 2017-02-04.
 *
 */

//This class handles logic for what happens when an alarm goes off
public class AlarmReceiver extends BroadcastReceiver {

    String alarmTag;
    String alarmName;
    Uri alarmRingtone;


    @Override
    //ths method is called by an alarm when it goes off. Gets necassar values from the alarm and then calls alarm coordinator to activate the alarm
    public void onReceive(Context context, Intent intent)
    {
        alarmTag = intent.getStringExtra("ALARM_TAG");
        alarmName = intent.getStringExtra("ALARM_NAME");
        alarmRingtone = intent.getParcelableExtra("ALARM_RINGTONE");


        Toast.makeText(context, "Alarm Activated", Toast.LENGTH_LONG).show();


        AlarmCoordinator.getInstance().activateAlarm(alarmTag, alarmName, alarmRingtone, context);

    }
}
