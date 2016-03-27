package com.cogeq.cogeqapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.CompoundButton;


/**
 * Created by cangiracoglu on 11/03/16.
 */
public class SettingsFragment extends Fragment {

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.settings_layout, container, false);

        SeekBar seekBarStart = (SeekBar) view.findViewById(R.id.seekBarStart);
        SeekBar seekBarFinish = (SeekBar) view.findViewById(R.id.seekBarFinish);
        final TextView startTime = (TextView) view.findViewById( R.id.startTime);
        final TextView endTime = (TextView) view.findViewById( R.id.endTime);

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.radioGroup1);
        radioGroup1.clearCheck();

        /* Attach CheckedChangeListener to radio group */
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                rb.setChecked(true);
            }
        });

        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.radioGroup2);
        radioGroup2.clearCheck();

        /* Attach CheckedChangeListener to radio group */
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                rb.setChecked(true);
            }
        });

        seekBarStart.setMax(24);
        seekBarFinish.setProgress(24);
        seekBarFinish.setMax(24);


        seekBarStart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                startTime.setText("Start : " + progress);
            }
        });

        seekBarFinish.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                endTime.setText("End : " + progress);
            }
        });


        return view;
    }
}