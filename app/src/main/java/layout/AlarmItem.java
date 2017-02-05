package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamawesome.seng403_alarmclock.AlarmSetActivity;
import com.teamawesome.seng403_alarmclock.ClockActivity;
import com.teamawesome.seng403_alarmclock.R;

/**
 * Activities that contain this fragment must implement the
 * {@link AlarmItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AlarmItem extends Fragment {

    private int alarmID;
    private Intent alarmData;

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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_alarm_item, container, false);
        constructInterface(view);
        return view;
    }

    private void constructInterface(View v) {
//        TextView tempTXT = (TextView) v.findViewById(R.id.tempText);
//        String inText = getArguments().getString(AlarmSetActivity.TEMP_TAG) + alarmID;
//        tempTXT.setText(inText);
    }

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
}
