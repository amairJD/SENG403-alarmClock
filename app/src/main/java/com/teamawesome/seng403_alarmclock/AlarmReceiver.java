package com.teamawesome.seng403_alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Eric Matteucci on 2017-02-04.
 *
 * This class currently does not do anything seeing as there are no AlarmManagers yet being used
 */

public class AlarmReceiver extends BroadcastReceiver {

    String alarmTag;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        alarmTag = intent.getStringExtra("ALARM_TAG");

        Toast.makeText(context, "Alarm Activated", Toast.LENGTH_LONG).show();

        // the intent should have the ringtone stored in it using a specific tag such as
        // TAG_RINGTONE. This is a simple way to be able to have different ringtones for the
        // alarms.

        AlarmCoordinator.getInstance().activateAlarm(alarmTag, context);

    }
}
