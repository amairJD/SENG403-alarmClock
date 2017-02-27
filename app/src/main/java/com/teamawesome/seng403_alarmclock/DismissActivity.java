package com.teamawesome.seng403_alarmclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Button;

/**
 * Created by Eric Matteucci on 2017-02-27.
 */

public class DismissActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dismiss_alarm);

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

            }
        });

    }
}
