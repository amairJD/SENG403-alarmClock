package com.teamawesome.seng403_alarmclock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import layout.AlarmItem;
import layout.AlarmListFragment;
import layout.ClockFragment;

//Amair Branch test

public class ClockActivity extends AppCompatActivity
        implements ClockFragment.OnFragmentInteractionListener,
                    AlarmListFragment.OnFragmentInteractionListener,
                    AlarmItem.OnFragmentInteractionListener
{

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

        AlarmCoordinator.getInstance().setActivity(this);

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

            if (position == 0)
                return new ClockFragment();
            else if (position == 1)
                return new AlarmListFragment();
            else
               return null;
        }

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

    public void showAlert(String alarmTag) {
        Intent myIntent = new Intent(this, DismissActivity.class);
        myIntent.putExtra("ALARM_TAG", alarmTag);
        startActivityForResult(myIntent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){
            if(resultCode == RESULT_OK){
                /**
                 * Returning from DismissActivity after alarm was activated
                 */
                String alarmTag = data.getExtras().getString("ALARM_TAG");
                int snoozeTime = data.getExtras().getInt("SNOOZE_TIME");

                Fragment alarmFrag = getSupportFragmentManager().findFragmentByTag(alarmTag);

                if (alarmFrag instanceof AlarmItem){
                    AlarmItem currentAlarm = (AlarmItem)alarmFrag;
                    if (snoozeTime != 0) currentAlarm.snoozeAlarm(snoozeTime);
                }


            }
        }

    }






}
