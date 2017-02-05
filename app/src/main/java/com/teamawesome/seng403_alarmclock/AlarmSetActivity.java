package com.teamawesome.seng403_alarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.DatePicker;

/**
 * This Activity is responsible ONLY for receiving user data and sending it to AlarmListFragment
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
//        EditText testText = (EditText) findViewById(R.id.testText);
//
//        String txt = testText.getText().toString();

        TimePicker timePicker = (TimePicker) findViewById(R.id.AS_timePicker);
        DatePicker datePicker = (DatePicker) findViewById(R.id.AS_datePicker);

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();


        /**
         * Store the variable inside the intent with it's corresponding TAG (see top of code).
         */
       // intent.putExtra(TEMP_TAG, txt);

        intent.putExtra(ALARM_HOUR_TAG, hour);
        intent.putExtra(ALARM_MINUTE_TAG, minute);
        intent.putExtra(ALARM_DAY_TAG, day);
        intent.putExtra(ALARM_MONTH_TAG, month);
        intent.putExtra(ALARM_YEAR_TAG, year);

        /**
         * Return the intent. IGNORE this.
         */
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelPressed(View view) {
       finish();
    }
}
