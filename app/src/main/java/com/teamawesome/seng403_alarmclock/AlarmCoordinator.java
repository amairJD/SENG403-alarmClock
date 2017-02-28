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

public class AlarmCoordinator {

    private Map ringtoneMap = new HashMap<Integer, Ringtone>();

    Ringtone ringtone;

    private ClockActivity clockActivity;

    private AlarmCoordinator() { }

    public void setActivity(ClockActivity clockActivity) { this.clockActivity = clockActivity; }

    private static AlarmCoordinator instance = new AlarmCoordinator();

    public static AlarmCoordinator getInstance() { return instance; }

    public void activateAlarm(String alarmTag, Context context) {

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        clockActivity.showAlert(alarmTag);
    }

    public void stopRingtone() {

        ringtone.stop();

    }

    public void setSnooze() {


    }





}
