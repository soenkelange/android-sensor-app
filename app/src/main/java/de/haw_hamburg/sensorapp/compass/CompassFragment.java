package de.haw_hamburg.sensorapp.compass;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import de.haw_hamburg.sensorapp.R;


public class CompassFragment extends Fragment implements Compass.OnAzimuthChangedListener {

    private Compass compass;
    private RadioButton hardwareSensorButton;
    private RadioButton softwareSensorButton;
    private SeekBar seekBar;
    private TextView progressTextView;
    private CompassView compassView;

    public CompassFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_compass, container, false);
        compassView = (CompassView) rootView.findViewById(R.id.compassView);
        hardwareSensorButton = (RadioButton) rootView.findViewById(R.id.hardwareSensorButton);
        softwareSensorButton = (RadioButton) rootView.findViewById(R.id.softwareSensorButton);
        progressTextView = (TextView) rootView.findViewById(R.id.progressTextView);
        progressTextView.setVisibility(TextView.INVISIBLE);
        if (compass == null) {
            compass = new Compass(getContext(), this);
        }
        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setEnabled(false);
        seekBar.setProgress((int) (compass.getLowPassFilter() * 100));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hardwareSensorButton.setOnClickListener(this::onRadioButtonClicked);
        softwareSensorButton.setOnClickListener(this::onRadioButtonClicked);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                compass.setLowPassFilter((float) i / 100);
                String formattedText = String.format(getString(R.string.compass_currentLowPassFilterValue), i);
                progressTextView.setText(formattedText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.hardwareSensorButton:
                if (checked) {
                    seekBar.setEnabled(true);
                    String formattedText = String.format(getString(R.string.compass_currentLowPassFilterValue), seekBar.getProgress());
                    progressTextView.setVisibility(TextView.VISIBLE);
                    progressTextView.setText(formattedText);
                    compass.setCurrentlyUsedSensor(Compass.USE_HARDWARE_SENSOR);
                }
                break;
            case R.id.softwareSensorButton:
                if (checked) {
                    seekBar.setEnabled(false);
                    progressTextView.setVisibility(TextView.INVISIBLE);
                    compass.setCurrentlyUsedSensor(Compass.USE_SOFTWARE_SENSOR);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            compass.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            compass.stop();
        }
    }

    @Override
    public void onAzimuthChanged(float azimuth) {
        compassView.rotateCompass(azimuth);
    }
}
