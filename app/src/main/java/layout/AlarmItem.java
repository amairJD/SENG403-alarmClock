package layout;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.teamawesome.seng403_alarmclock.AlarmReceiver;
import com.teamawesome.seng403_alarmclock.AlarmSetActivity;
import com.teamawesome.seng403_alarmclock.R;

import java.io.Serializable;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 *
 * This fragment contains the logic for an individual alarm and is created as a fragment
 * whenever a new alarm is created
 *
 * Activities that contain this fragment must implement the
 * {@link AlarmItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AlarmItem extends Fragment implements Serializable{

    /**
     * Alarm parameters
     */
    private int alarmHour;
    private int alarmMin;
    private int alarmYear;
    private int alarmMonth;
    private int alarmDay;
    private Repeat alarmRepeat;
    private String alarmName;
    private boolean switchStatus = true;

    PendingIntent pendingIntent;
    AlarmManager aManager;
    Uri ringtone;

    private Switch switchButton;

    private OnFragmentInteractionListener mListener;

    /**
     * When creating alarms, this function must be used and not the constructor
     * @param data --bundle of data containing alarm parameters
     * @return new AlarmItem
     */
    public static AlarmItem newInstance(Intent data){
        AlarmItem alarmItem = new AlarmItem();
        Bundle args = data.getExtras();
        alarmItem.setArguments(args);
        return alarmItem;
    }

    public AlarmItem(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * logic for the visible representation of the alarm item (switches, buttons, etc.)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_item, container, false);
        updateAlarmInfo();
        constructAlarmInterface(view);

        /**
         * Logic for any alarmItem buttons must be placed here instead of using onClick in XML.
         * See below for example.
         */

        // Edit/settings button logic
        FloatingActionButton settingsButton = (FloatingActionButton) view.findViewById(R.id.AI_settingsButton);
        settingsButton.setOnClickListener( new View.OnClickListener() {
           public void onClick(View v) {
               // If the alarm edit button is pressed, start AlarmSetActivity
               // to receive new information in onActivityResult(...)
               Intent intent = new Intent(getActivity(), AlarmSetActivity.class);
               startActivityForResult(intent, 1);
           }
        });

        /**
         * Logic for on/off toggle on alarm
         */
        switchButton = (Switch) view.findViewById(R.id.AI_switchButton);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Alarm button is on, schedule alarm
                    scheduleAlarm();
                    switchStatus = true;
                }
                else {
                    if (aManager != null)
                        aManager.cancel(pendingIntent); // cancel alarm if it was set
                    switchStatus = false;
                }
            }
        });

        //update switchButton
        if (!switchStatus){
            switchButton.setChecked(false);
        }
        else
            switchButton.setChecked(true);


        return view;
    }

    /**
     * when another activity is called, this class is used to return data/intents from that activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            /**
             * If alarm was edited, this occurs on return from AlarmSetActivity
             */
            if(resultCode == RESULT_OK){
                Bundle args = data.getExtras();
                this.getArguments().putAll(args);
                updateAlarmInfo();
                constructAlarmInterface(getView());
            }
        }
    }


    /**
     * Using this fragments Arguments, update local parameters like the Alarm's time and date.
     */
    private void updateAlarmInfo(){
        alarmHour = getArguments().getInt(AlarmSetActivity.ALARM_HOUR_TAG);
        alarmMin = getArguments().getInt(AlarmSetActivity.ALARM_MINUTE_TAG);
        alarmYear = getArguments().getInt(AlarmSetActivity.ALARM_YEAR_TAG);
        alarmMonth = getArguments().getInt(AlarmSetActivity.ALARM_MONTH_TAG);
        alarmDay = getArguments().getInt(AlarmSetActivity.ALARM_DAY_TAG);

        String name = getArguments().getString(AlarmSetActivity.ALARM_NAME_TAG);
        if (name != null && !name.isEmpty())
            alarmName = name;

        ringtone = getArguments().getParcelable(AlarmSetActivity.ALARM_RINGTONE_TAG);

        alarmRepeat = (Repeat) getArguments().get(AlarmSetActivity.ALARM_REPEAT_TAG);

        if (!(getArguments().getBoolean(AlarmSetActivity.ALARM_SWITCH_STATUS))){
            switchStatus = false;
        }
    }

    /**
     * Interface of each individual clock item is set here.
     * I.e take the data from the arguments and set display accordingly
     *
     * @param v
     */
    private void constructAlarmInterface(View v) {
        TextView alarmTime = (TextView) v.findViewById(R.id.AI_TimeTextView);

        if (alarmMin < 10)
            alarmTime.setText(alarmHour + ":0" + alarmMin);
        else
            alarmTime.setText(alarmHour + ":" + alarmMin);


        TextView alarmDate = (TextView) v.findViewById(R.id.AI_DateTextView);

        TextView alarmNameTextView = (TextView) v.findViewById(R.id.AI_NameTextView);

        switch(alarmRepeat) {
            case NONE: alarmDate.setText(getFormattedDate());
                break;
            case DAILY: alarmDate.setText("Every Day");
                break;
            case WEEKLY: alarmDate.setText("Every " + getFormattedDay());
                break;
            case TEST_EVERY_MINUTE: alarmDate.setText("(TEST) Every Minute");
                break;
            default: alarmDate.setText(getFormattedDate());
                break;
        }

        if (alarmName != null && !alarmName.isEmpty())
            alarmNameTextView.setText(alarmName);

        scheduleAlarm();

    }

    /**
     * Snoozes the current alarm some amount of minutes in the future
     * @param minutes
     */
    public void snoozeAlarm(int minutes){
        if (alarmMin < (60-minutes)){
            alarmMin += minutes;
        }
        else if (alarmHour < 23){
            alarmHour++;
            alarmMin = alarmMin + minutes - 60;
        }
        /**
         * Note: their will be issues if the alarm was set for exactly the end of the month or the year
         * in that case, something like above should be implemented.
         */

        // Reconstruct alarm interface
        constructAlarmInterface(getView());
    }

    /**
     * Switches off alarm
     */
    public void switchOff() {
        switchButton.setChecked(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Returns information about alarm in a string, used in ClockActivity.java to store Alarm info
     * in a file when closing.
     * @return
     */
    public String retrieveInfo() {
        return "" + alarmHour + "/"
                + alarmMin + "/"
                + 0 + "/"
                + alarmDay + "/"
                + alarmMonth + "/"
                + alarmYear + "/"
                + alarmName + "/"
                + Repeat.toInt(alarmRepeat) + "/"
                + switchButton.isChecked();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    /**
     * returns the alarms set date in a formatted string
     * @return
     */
    private String getFormattedDate() {

        String monthString;
        switch (alarmMonth) {
            case 0:  monthString = "January";
                break;
            case 1:  monthString = "February";
                break;
            case 2:  monthString = "March";
                break;
            case 3:  monthString = "April";
                break;
            case 4:  monthString = "May";
                break;
            case 5:  monthString = "June";
                break;
            case 6:  monthString = "July";
                break;
            case 7:  monthString = "August";
                break;
            case 8:  monthString = "September";
                break;
            case 9: monthString = "October";
                break;
            case 10: monthString = "November";
                break;
            case 11: monthString = "December";
                break;
            default: monthString = "Invalid month";
                break;
        }

        return (monthString + " " + alarmDay + ", " + alarmYear);

    }

    /**
     * Returns the day of the week the alarm is set for
     * @return
     */
    private String getFormattedDay() {

        Calendar cal = Calendar.getInstance();
        cal.set(alarmYear, alarmMonth, alarmDay);

        int weekDay = cal.get(Calendar.DAY_OF_WEEK);


        String dayString;
        switch (weekDay) {
            case 1: dayString = "Sunday";
                break;
            case 2: dayString = "Monday";
                break;
            case 3: dayString = "Tuesday";
                break;
            case 4: dayString = "Wednesday";
                break;
            case 5: dayString = "Thursday";
                break;
            case 6: dayString = "Friday";
                break;
            case 7: dayString = "Saturday";
                break;
            default: dayString = "";
                break;
        }

        return dayString;

    }

    //returns the repeat state(daily, weekly, etc.) of the alarmm
    public enum Repeat {
        NONE, DAILY, WEEKLY, TEST_EVERY_MINUTE;

        public static Repeat fromInt(int x) {
            switch(x) {
                case 0:
                    return NONE;
                case 1:
                    return DAILY;
                case 2:
                    return WEEKLY;
                case 3:
                    return TEST_EVERY_MINUTE;
            }
            return null;
        }

        //turns a return state enum into an int
        public static int toInt(Repeat repeat) {
            switch(repeat) {
                case NONE:
                    return 0;
                case DAILY:
                    return 1;
                case WEEKLY:
                    return 2;
                case TEST_EVERY_MINUTE:
                    return 3;
            }
            return -1;
        }
    }

    /**
     * Schedules alarm with the current alarm values using alarmManager
     */
    private void scheduleAlarm() {

        //schedule alarm date
        Calendar alarmCal = Calendar.getInstance();
        alarmCal.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMin, 0);

        if (alarmCal.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
            return;
        }

        //store necessary data( alarm name, ringtone, etc.) in alarm
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("ALARM_TAG", getTag());
        intent.putExtra("ALARM_NAME", alarmName);
        intent.putExtra("ALARM_RINGTONE", (Parcelable)ringtone);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), Integer.parseInt(getTag()),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        aManager = (AlarmManager)getActivity().getSystemService(Activity.ALARM_SERVICE);

        //enable alarm based on the repeat setting of the alarm
        switch(alarmRepeat) {
            case NONE: aManager.set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pendingIntent);
                break;
            case DAILY: aManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
                break;
            case WEEKLY: aManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000, pendingIntent);
                break;
            case TEST_EVERY_MINUTE: aManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), 60 * 1000, pendingIntent);
                break;
            default: aManager.set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pendingIntent);
                break;
        }

    }
}
