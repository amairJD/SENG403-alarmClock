package layout;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import com.teamawesome.seng403_alarmclock.AlarmSetActivity;
import com.teamawesome.seng403_alarmclock.ClockActivity;
import com.teamawesome.seng403_alarmclock.R;

import static android.app.Activity.RESULT_OK;

/**
 * Activities that contain this fragment must implement the
 * {@link AlarmItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AlarmItem extends Fragment {

    /** NOTE:
     *  See note in setID() regarding alarmID's
     */
    private int alarmID;

    private int alarmHour;
    private int alarmMin;
    private int alarmYear;
    private int alarmMonth;
    private int alarmDay;

    private OnFragmentInteractionListener mListener;

    public static AlarmItem newInstance(Intent data){
        AlarmItem alarmItem = new AlarmItem();
        Bundle args = data.getExtras();
        alarmItem.setArguments(args);
        return alarmItem;
    }

    public AlarmItem(){
        setID();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        FloatingActionButton settingsButton = (FloatingActionButton) view.findViewById(R.id.AI_settingsButton);
        settingsButton.setOnClickListener( new View.OnClickListener() {
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), AlarmSetActivity.class);
               startActivityForResult(intent, 1);
           }
        });

        Switch switchButton = (Switch) view.findViewById(R.id.AI_switchButton);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // turn alarm on
                }
                else {
                    // turn alarm off
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
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
        alarmDate.setText(getFormattedDate());
    }

    /**
     * NOTE: setID function needs to be rewritten, as problems will arise when alarms are removed.
     * I.e Say you have 3 alarms. So each alarm has ID's 0,1,2. Say you delete alarm 0 and
     * numberofAlarms is now 2. So now you have alarm ID's 1 and 2 being used. The next Alarm you
     * create will attempt to create an alarm of ID 2 - which is already in use.
     * Someone please volunteer to solve this problem :)
     *
     * On initial creation, set's an available alarm ID and increments number of Alarms
     */
    private void setID() {
        alarmID = ClockActivity.numberOfAlarms;
        ClockActivity.numberOfAlarms++;
        Log.i("alrmID", "Alarm added, alarmID is: " + alarmID + ". numberOfAlarms has been incremented");
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
