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

public class AlarmReceiver extends BroadcastReceiver {

    String alarmTag;
    String alarmName;
    Uri alarmRingtone;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        alarmTag = intent.getStringExtra("ALARM_TAG");
        alarmName = intent.getStringExtra("ALARM_NAME");
        alarmRingtone = intent.getParcelableExtra("ALARM_RINGTONE");


        Toast.makeText(context, "Alarm Activated", Toast.LENGTH_LONG).show();

        // the intent should have the ringtone stored in it using a specific tag such as
        // TAG_RINGTONE. This is a simple way to be able to have different ringtones for the
        // alarms.

        AlarmCoordinator.getInstance().activateAlarm(alarmTag, alarmName, alarmRingtone, context);

    }
}
