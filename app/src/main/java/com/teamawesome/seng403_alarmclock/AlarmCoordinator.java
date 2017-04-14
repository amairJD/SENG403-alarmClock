package com.teamawesome.seng403_alarmclock;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Helps coordinate data between the various alarm actiities
 *
 * Created by Eric Matteucci on 2017-02-12.
 */

public class AlarmCoordinator {

    private Map ringtoneMap = new HashMap<Integer, Ringtone>();
    Ringtone ringtone;
    private ClockActivity clockActivity;

    private AlarmCoordinator() {
        // Required empty constructor
    }

    public void setActivity(ClockActivity clockActivity) {
        this.clockActivity = clockActivity;
    }

    private static AlarmCoordinator instance = new AlarmCoordinator();

    public static AlarmCoordinator getInstance() {
        return instance;
    }

    /**
     * Responsible for activating the alarm based on the data passed to it from alarm reciever
     *
     * @param alarmTag -- the id of the alarm
     * @param alarmName -- the name of the alarm
     * @param context
     */
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

}
