package com.teamawesome.seng403_alarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AlarmSetActivity extends AppCompatActivity {

    /**
     * A TAG should be created for each type of data taken.
     * FOR EXAMPLE:
     *  - user has entered MEGA_ALARM as their alarm name
     *  - You must create a final public static String called "ALARM_NAME_TAG"
     *          (or something similar)
     */
    final public static String TEMP_TAG = "TEMP_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);
    }

    /**
     *
     * When the 'OK' button is pressed, this function is called.
     * The fucntion is responsible for ensuring that the user's alarm settings are transferred to
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
        EditText testText = (EditText) findViewById(R.id.testText);
        String txt = testText.getText().toString();

        /**
         * Store the variable inside the intent with it's corresponding TAG (see top of code).
         */
        intent.putExtra(TEMP_TAG, txt);

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
