package com.teamawesome.seng403_alarmclock;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.DatePicker;

import layout.AlarmItem;

/**
 * This Activity is responsible ONLY for receiving user data and sending it to AlarmListFragment
 * Created by: Amair Javaid
 *
 * Handles the gathering of data needed to set the alarm from the user
 */
public class AlarmSetActivity extends AppCompatActivity {

     Intent returnIntent;
    /**
     * A TAG should be created for each type of data taken.
     * FOR EXAMPLE:
     *  - user has entered MEGA_ALARM as their alarm name
     *  - You must create a final public static String called "ALARM_NAME_TAG"
     *          (or something similar)
     * These tags are used to pass user data back to the alarm
     */
    final public static String TEMP_TAG = "TEMP_TAG";

    final public static String ALARM_DAY_TAG = "ALARM_DAY_TAG";
    final public static String ALARM_MONTH_TAG = "ALARM_MONTH_TAG";
    final public static String ALARM_YEAR_TAG = "ALARM_YEAR_TAG";
    final public static String ALARM_MINUTE_TAG = "ALARM_MINUTE_TAG";
    final public static String ALARM_HOUR_TAG = "ALARM_HOUR_TAG";
    final public static String ALARM_SECONDS_TAG = "ALARM_SECONDS_TAG";
    final public static String ALARM_NAME_TAG = "ALARM_NAME_TAG";
    final public static String ALARM_REPEAT_TAG = "ALARM_REPEAT_TAG";
    final public static String ALARM_SWITCH_STATUS = "ALARM_SWITCH_STAT_TAG";
    final public static String ALARM_RINGTONE_TAG = "ALARM_RINGTONE_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);
        returnIntent = new Intent();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     *
     * When the 'OK' button is pressed, this function is called.
     * The function is responsible for ensuring that the user's alarm settings are transferred to
     * the AlarmListFragment successfully, where the interface for the new AlarmItem will be drawn
     *
     * @param view
     */
    public void okPressed(View view) {

        /**
         * Receive input.
         * FOR EXAMPLE
         *  - User has picked a value from the time picker
         *  - Store that value in a variable that will be used in the next step
         */

        TimePicker timePicker = (TimePicker) findViewById(R.id.AS_timePicker);
        DatePicker datePicker = (DatePicker) findViewById(R.id.AS_datePicker);

        EditText editText = (EditText) findViewById(R.id.AS_nameEditText);
        String alarmName = editText.getText().toString();

        Spinner spinner = (Spinner) findViewById(R.id.repeatSpinner);
        String repeatString = (String) spinner.getSelectedItem();
        AlarmItem.Repeat repeat;
        switch(repeatString){
            case "Never": repeat = AlarmItem.Repeat.NONE;
                break;
            case "Daily": repeat = AlarmItem.Repeat.DAILY;
                break;
            case "Weekly": repeat = AlarmItem.Repeat.WEEKLY;
                break;
            case "(TEST) Minutely": repeat = AlarmItem.Repeat.TEST_EVERY_MINUTE;
                break;
            default: repeat = AlarmItem.Repeat.NONE;
                break;
        }


        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        int seconds = 0;
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();


        //Gets chosen ringtone or a default ringtone if no ringtone was chosen
        Uri currentRingtone = returnIntent.getParcelableExtra(ALARM_RINGTONE_TAG);
        if (currentRingtone == null)
        {
            returnIntent.putExtra(ALARM_RINGTONE_TAG, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        }


        /**
         * Store the variable inside the intent with it's corresponding TAG (see top of code).
         */

        returnIntent.putExtra(ALARM_HOUR_TAG, hour);
        returnIntent.putExtra(ALARM_MINUTE_TAG, minute);
        returnIntent.putExtra(ALARM_SECONDS_TAG, seconds);
        returnIntent.putExtra(ALARM_DAY_TAG, day);
        returnIntent.putExtra(ALARM_MONTH_TAG, month);
        returnIntent.putExtra(ALARM_YEAR_TAG, year);
        returnIntent.putExtra(ALARM_NAME_TAG, alarmName);
        returnIntent.putExtra(ALARM_REPEAT_TAG, repeat);
        returnIntent.putExtra(ALARM_SWITCH_STATUS, true);


        /**
         * Return the intent.
         */
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    //user cancelled activity with the cancel button
    public void cancelPressed(View view) {
       finish();
    }

    //creates an activity which allows the user to choose a ringtone from the alarm
    public void chooseRingtonePressed(View view) {

        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone");

        Uri currentRingtone =  RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentRingtone);

        startActivityForResult(intent, 1);
    }


    /**
     * Retrieves data from an activity started with startActivityforResult(..)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            //ringtone choice activity
            if (requestCode ==1)
            {
                returnIntent.putExtra(ALARM_RINGTONE_TAG, data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI));
            }
        }
    }
}
