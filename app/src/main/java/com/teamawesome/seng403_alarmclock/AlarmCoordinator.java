package com.teamawesome.seng403_alarmclock;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import layout.AlarmItem;

/**
 * Created by Eric Matteucci on 2017-02-12.
 */

//a class for helping to coordinate data between the various alarm actiities
public class AlarmCoordinator {

    private Map ringtoneMap = new HashMap<Integer, Ringtone>();

    Ringtone ringtone;

    private ClockActivity clockActivity;

    private AlarmCoordinator() { }

    public void setActivity(ClockActivity clockActivity) { this.clockActivity = clockActivity; }

    private static AlarmCoordinator instance = new AlarmCoordinator();

    public static AlarmCoordinator getInstance() { return instance; }

    //this activity is responsible for activating the alarm based on the data passed to it from alarm reciever
    public void activateAlarm(String alarmTag, String alarmName, Uri alarmRingtone, Context context) {

        //plays the specified ringtone, or a default one if none is specified)
        Uri alarmUri = alarmRingtone;
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will show a popup for the alarm to be snoozed/dismissed
        clockActivity.showAlert(alarmTag, alarmName);
    }

    //stops the alarm ringtone from playing anymore
    public void stopRingtone() {

        ringtone.stop();

    }

    //this method would be for choosing a snooze period before snoozing
    public void setSnooze() {


    }





}
