package com.teamawesome.seng403_alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Button;

import layout.AlarmItem;

/**
 * Created by Eric Matteucci on 2017-02-27.
 */

public class DismissActivity extends AppCompatActivity {

    String alarmTag;
    final int snoozeTime = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dismiss_alarm);

        alarmTag = getIntent().getStringExtra("ALARM_TAG");
        final Intent intent = new Intent();
        intent.putExtra("ALARM_TAG", alarmTag);
        intent.putExtra("SNOOZE_TIME", 0);

        SeekBar dismissBar = (SeekBar) findViewById(R.id.dismiss_seekBar);
        dismissBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= 90) {
                    AlarmCoordinator.getInstance().stopRingtone();
                    finish();
                }
                else {
                    seekBar.setProgress(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button snoozeButton = (Button) findViewById(R.id.snooze_button);

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
