package com.teamawesome.seng403_alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.TextView;



/**
 * Created by Eric Matteucci on 2017-02-27.
 */

//activity for showng the alarm dismiss/cancel popup
public class DismissActivity extends AppCompatActivity {

    String alarmTag;
    final int snoozeTime = 1;

    @Override
    //when the dialogue is created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dismiss_alarm);

        //readstore important variables
        alarmTag = getIntent().getStringExtra("ALARM_TAG");
        String alarmName = getIntent().getStringExtra("ALARM_NAME");
        final Intent intent = new Intent();
        intent.putExtra("ALARM_TAG", alarmTag);
        intent.putExtra("SNOOZE_TIME", 0);

        //display alarm name
        TextView textView = (TextView) findViewById(R.id.DA_nameTextView);
        textView.setText(alarmName);


        //display dismiss bar
        SeekBar dismissBar = (SeekBar) findViewById(R.id.dismiss_seekBar);
        dismissBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //methods for the dismiss bar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() >= 90) {
                    AlarmCoordinator.getInstance().stopRingtone();
                    finish();
                }
                else {
                    seekBar.setProgress(0);
                }
            }
        });

        //display snooze button
        Button snoozeButton = (Button) findViewById(R.id.snooze_button);

        //what happens when snooze is pressed
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmCoordinator.getInstance().stopRingtone();
                intent.putExtra("SNOOZE_TIME", snoozeTime);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
