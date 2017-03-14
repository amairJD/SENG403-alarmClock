package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamawesome.seng403_alarmclock.AlarmSetActivity;
import com.teamawesome.seng403_alarmclock.ClockActivity;
import com.teamawesome.seng403_alarmclock.R;

import static android.app.Activity.RESULT_OK;

/***
 *
 * IGNORE mostly for now, unless you absolutely need to edit.
 *
 */



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlarmListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AlarmListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlarmListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmListFragment newInstance(String param1, String param2) {
        AlarmListFragment fragment = new AlarmListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        FloatingActionButton addAlarmButton =
                (FloatingActionButton) rootView.findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("TEST", "FAB_pressed");
                    Intent intent = new Intent(getActivity(), AlarmSetActivity.class);
                    startActivityForResult(intent, 1);
                }
        }
        );

        return rootView;
    }


    @Override
    public void onResume(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(ClockActivity.ALARMDATA_FILENAME, Context.MODE_PRIVATE);

        String alarmData = prefs.getString(""+ClockActivity.alarmCounterForID, null);
        if (alarmData == null){
            Log.i("CHKR", "failure" + ""+ClockActivity.alarmCounterForID);
        }
        while (alarmData != null){
            /**
             * NOTE: Issues may occur reagrding setting tags when we want to DELETE alarms,
             * FIX LATER
             */

            String[] parts = alarmData.split(" */ *");
            Log.i("CHKR", "recreation time");

            Intent data = new Intent();
            data.putExtra(AlarmSetActivity.ALARM_HOUR_TAG, Integer.parseInt(parts[0]));
            data.putExtra(AlarmSetActivity.ALARM_MINUTE_TAG, Integer.parseInt(parts[1]));
            data.putExtra(AlarmSetActivity.ALARM_SECONDS_TAG, Integer.parseInt(parts[2]));
            data.putExtra(AlarmSetActivity.ALARM_DAY_TAG, Integer.parseInt(parts[3]));
            data.putExtra(AlarmSetActivity.ALARM_MONTH_TAG, Integer.parseInt(parts[4]));
            data.putExtra(AlarmSetActivity.ALARM_YEAR_TAG, Integer.parseInt(parts[5]));
            data.putExtra(AlarmSetActivity.ALARM_NAME_TAG, parts[6]);
            data.putExtra(AlarmSetActivity.ALARM_REPEAT_TAG,
                    AlarmItem.Repeat.fromInt(Integer.parseInt(parts[7])));
            data.putExtra(AlarmSetActivity.ALARM_SWITCH_STATUS, Boolean.parseBoolean(parts[8]));

            Fragment AlarmItem = layout.AlarmItem.newInstance(data);
            LinearLayout alarmList = (LinearLayout) getActivity().findViewById(R.id.alarmItemList);
            FragmentManager fragManager = getFragmentManager();
            FragmentTransaction fragTransaction = fragManager.beginTransaction();
            String alarmTag = "" + ClockActivity.alarmCounterForID;
            fragTransaction.add(alarmList.getId(), AlarmItem, alarmTag);
            Log.i("alrmTAG", "New Alarm created. set Tag as: " + alarmTag);
            ClockActivity.alarmCounterForID++;
            ClockActivity.numberOfAlarms++;
            fragTransaction.commit();

            alarmData = prefs.getString(""+ClockActivity.alarmCounterForID, null);
        }



        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                Fragment AlarmItem = layout.AlarmItem.newInstance(data);
                LinearLayout alarmList = (LinearLayout) getActivity().findViewById(R.id.alarmItemList);
                FragmentManager fragManager = getFragmentManager();
                FragmentTransaction fragTransaction = fragManager.beginTransaction();
                String alarmTag = "" + ClockActivity.alarmCounterForID;
                fragTransaction.add(alarmList.getId(), AlarmItem, alarmTag);
                Log.i("alrmTAG", "New Alarm created. set Tag as: " + alarmTag);
                ClockActivity.alarmCounterForID++;
                ClockActivity.numberOfAlarms++;
                fragTransaction.commit();
            }
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
