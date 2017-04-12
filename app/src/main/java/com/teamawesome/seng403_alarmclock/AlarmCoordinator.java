package com.teamawesome.seng403_alarmclock;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Coordinates alarms by activating them and controlling their ringtones.
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
     * Activates an alarm
     *
     * @param alarmTag -- the id of the alarm
     * @param alarmName -- the name of the alarm
     * @param context
     */
    public void activateAlarm(String alarmTag, String alarmName, Context context) {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        clockActivity.showAlert(alarmTag, alarmName);
    }

    public void stopRingtone() {

        ringtone.stop();

    }

}
