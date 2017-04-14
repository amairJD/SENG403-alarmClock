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

    /** specifies which alarm has been triggered. Needed for the snoozing of an alarm, since the clock activity
     *  does not know which has triggered.
     */
    String alarmTag;

    /**
     * The label on this alarm, needed to be displayed on the dismiss alert window.
     */
    String alarmName;

    /**
     * Defines what is to happen when the alarm is triggered.
     * @param context
     * @param intent Used to store passed information in the intent's bundle.
     */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // retrieve the alarmTag and alarmName from the intent to be passed to the AlarmCoordinator
        alarmTag = intent.getStringExtra("ALARM_TAG");
        alarmName = intent.getStringExtra("ALARM_NAME");

        // create a toast to alert the user that the alarm has triggered
        Toast.makeText(context, "Alarm Activated", Toast.LENGTH_LONG).show();

        // send the information to the AlarmCoordinator, which will set the flow of events for the triggered alarm
        AlarmCoordinator.getInstance().activateAlarm(alarmTag, alarmName, context);

    }
}
