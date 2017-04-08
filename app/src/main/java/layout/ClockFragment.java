package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextClock;

import com.teamawesome.seng403_alarmclock.R;


/***
 *
 * IGNORE mostly for now, unless you absolutely need to edit.
 *
 */




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClockFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClockFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // Clock Fragment Fields - SC
    private static TextClock digital;
    private static AnalogClock analogue;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClockFragment newInstance(String param1, String param2) {
        ClockFragment fragment = new ClockFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_clock, container, false);
        onButtonClickListener(v);
        return v;
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

    /**
     * This method listens for an event to switch the clock from analogue to digital
     * and vice versa.  - SC)
     */
    public void onButtonClickListener(View v) {

        // load/assign the items in the fragment view that will be manipulated
        digital = (TextClock) v.findViewById(R.id.textClock);
        analogue = (AnalogClock) v.findViewById(R.id.analogClock);


        // When you Click on the DIGITAL Clock Face this is what happens:
        digital.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (digital.getVisibility() == TextClock.VISIBLE) {
                            digital.setVisibility(TextClock.GONE);
                            analogue.setVisibility(AnalogClock.VISIBLE);
                        } else {
                            digital.setVisibility(TextClock.VISIBLE);
                            analogue.setVisibility(AnalogClock.GONE);
                        }
                    }
                }
        ); // end of digital on click


        // When you Click on the ANALOG Clock Face this is what happens:
        analogue.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (digital.getVisibility() == TextClock.VISIBLE) {
                            digital.setVisibility(TextClock.GONE);
                            analogue.setVisibility(AnalogClock.VISIBLE);
                        } else {
                            digital.setVisibility(TextClock.VISIBLE);
                            analogue.setVisibility(AnalogClock.GONE);
                        }
                    }
                }
        ); //end of analog clock on click

    }

}
