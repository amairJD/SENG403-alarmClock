package com.teamawesome.seng403_alarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.DatePicker;

import layout.AlarmItem;

/**
 * This Activity is responsible ONLY for receiving user data and sending it to AlarmListFragment
 * Created by: Amair Javaid
 */
public class AlarmSetActivity extends AppCompatActivity {

    /**
     * A TAG should be created for each type of data taken.
     * FOR EXAMPLE:
     *  - user has entered MEGA_ALARM as their alarm name
     *  - You must create a final public static String called "ALARM_NAME_TAG"
     *          (or something similar)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);
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
        Intent intent = new Intent();

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


        /**
         * Store the variable inside the intent with it's corresponding TAG (see top of code).
         */

        intent.putExtra(ALARM_HOUR_TAG, hour);
        intent.putExtra(ALARM_MINUTE_TAG, minute);
        intent.putExtra(ALARM_SECONDS_TAG, seconds);
        intent.putExtra(ALARM_DAY_TAG, day);
        intent.putExtra(ALARM_MONTH_TAG, month);
        intent.putExtra(ALARM_YEAR_TAG, year);
        intent.putExtra(ALARM_NAME_TAG, alarmName);
        intent.putExtra(ALARM_REPEAT_TAG, repeat);

        /**
         * Return the intent.
         */
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelPressed(View view) {
       finish();
    }
}
