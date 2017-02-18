package com.teamawesome.seng403_alarmclock;

import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import layout.AlarmItem;
import layout.AlarmListFragment;
import layout.ClockFragment;


/***
 * IGNORE mostly for now, unless you absolutely need to edit.
 */


public class ClockActivity extends AppCompatActivity
        implements ClockFragment.OnFragmentInteractionListener,
        AlarmListFragment.OnFragmentInteractionListener,
        AlarmItem.OnFragmentInteractionListener {

    /**
     * Whenever a new Alarm is created, this global int is assigned to it as a ID and the Alarm is
     * now known as Alarm #(ID).
     * numberOfAlarms is then incremented.
     * This happens in AlarmListFragment, but is written here for reference.
     */
    public static int alarmCounterForID = 0;
    public static int numberOfAlarms = 0;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //onClockTapListener();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //onClockTapListener();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            if (position == 0) {
                //onClockTapListener();
                return new ClockFragment();
            }
            else if (position == 1)
                return new AlarmListFragment();
            else
                return null;
        }

        //onClockTapListener();

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }


    // Clock Fragment Fields

    private static Button buttonSwitchClock;
    private static TextClock digital;
    private static AnalogClock analogue;


    /**
     * This method listens for an event to switch the clock from analogue to digital
     * and vice versa.  (I intend to make it switch without a button by just tapping the
     * clock widgets but for now I am just doing it with a button to test if it works
     * Also, I don't know if you can even click widgets & update in a full page fragment... - Steve)
     *
     * NOTE: I CAN NOT FIGURE OUT WHERE TO PUT THE CALL TO THIS FUNCTION
     *      -- In an activity it would go in onCreate(). But it just crashes the app if i do that.
     *      -- Maybe it would go in onActivityCreated(Bundle)?? But I dont know..
     *
     */
    public void onClockTapListener() {

        // load/assign the items in the fragment view that will be manipulated
        buttonSwitchClock = (Button) findViewById(R.id.buttonSwClID);
        digital = (TextClock) findViewById(R.id.textClock);
        analogue = (AnalogClock) findViewById(R.id.analogClock);

        // set the action of the click
        buttonSwitchClock.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (digital.getVisibility() == TextClock.GONE) {
                            digital.setVisibility(TextClock.VISIBLE);
                            analogue.setVisibility(AnalogClock.GONE);
                        } else {
                            digital.setVisibility(TextClock.GONE);
                            analogue.setVisibility(AnalogClock.VISIBLE);
                        }
                    }
                }
        );
    }


}
