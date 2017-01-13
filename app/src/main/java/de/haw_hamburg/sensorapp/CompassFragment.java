package de.haw_hamburg.sensorapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;


public class CompassFragment extends Fragment implements Compass.OnAzimuthChangedListener{

    private Compass compass;
    private RadioButton hardwareSensorButton;
    private RadioButton softwareSensorButton;
    private SeekBar seekBar;
    private TextView progressTextView;
    private CompassView compassView;

    public CompassFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_compass, container, false);
        compassView = (CompassView)rootView.findViewById(R.id.compassView);
        hardwareSensorButton = (RadioButton)rootView.findViewById(R.id.hardwareSensorButton);
        softwareSensorButton = (RadioButton)rootView.findViewById(R.id.softwareSensorButton);
        seekBar = (SeekBar)rootView.findViewById(R.id.seekBar);
        progressTextView = (TextView)rootView.findViewById(R.id.progressTextView);
        progressTextView.setVisibility(TextView.INVISIBLE);
        seekBar.setMax(100);
        seekBar.setEnabled(false);

        if (compass == null){
            compass = new Compass(getContext(), this);
        }
        seekBar.setProgress((int)(compass.getLowPassFilter()*100));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hardwareSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });
        softwareSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                compass.setLowPassFilter((float)i/100);
                progressTextView.setText(getResources().getStringArray(R.array.compass_fragment_textView)[0]+i+getResources().getStringArray(R.array.compass_fragment_textView)[1]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.hardwareSensorButton:
                if(checked){
                    seekBar.setEnabled(true);
                    progressTextView.setVisibility(TextView.VISIBLE);
                    progressTextView.setText(getResources().getStringArray(R.array.compass_fragment_textView)[0]+seekBar.getProgress()+getResources().getStringArray(R.array.compass_fragment_textView)[1]);
                    compass.setCurrentlyUsedSensor(compass.USE_HARDWARE_SENSOR);
                }
                break;
            case R.id.softwareSensorButton:
                if(checked){
                    seekBar.setEnabled(false);
                    progressTextView.setVisibility(TextView.INVISIBLE);
                    compass.setCurrentlyUsedSensor(compass.USE_SOFTWARE_SENSOR);
                }
                break;
        }
    }

    public void onAzimuthChanged(float azimuth){
        compassView.rotateCompass(azimuth);
    }

    @Override
    public void onPause(){
        super.onPause();
        compass.stop();
    }

    @Override
    public void onResume(){
        super.onResume();
        compass.start();
    }

}
