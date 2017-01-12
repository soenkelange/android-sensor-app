package de.haw_hamburg.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;


public class CompassFragment extends Fragment{

    private Compass compass;
    private RadioButton hardwareSensorButton;
    private RadioButton softwareSensorButton;
    private SeekBar seekBar;
    private TextView progressTextView;

    public CompassFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_compass, container, false);
        hardwareSensorButton = (RadioButton)rootView.findViewById(R.id.hardwareSensorButton);
        softwareSensorButton = (RadioButton)rootView.findViewById(R.id.softwareSensorButton);
        seekBar = (SeekBar)rootView.findViewById(R.id.seekBar);
        progressTextView = (TextView)rootView.findViewById(R.id.progressTextView);
        progressTextView.setVisibility(TextView.INVISIBLE);
        seekBar.setMax(100);
        seekBar.setEnabled(false);

        if (compass == null){
            compass = new Compass(getContext());
            compass.compassView = (ImageView)rootView.findViewById(R.id.compass);
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
                    seekBar.setProgress((int)(compass.getLowPassFilter()*100));
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

    class Compass implements SensorEventListener{
        private SensorManager sensorManager;
        private Sensor gravitiySensor;
        private Sensor magneticSensor;
        private Sensor rotationVectorSensor;
        private float[] currentGravityValues = new float[3];
        private float[] currentMagneticValues = new float[3];
        private float azimuth = 0;
        private float currentAzimuth = 0;
        private ImageView compassView = null;
        private int currentlyUsedSensor;
        private float lowPassFilter;
        private final int USE_HARDWARE_SENSOR = 1001;
        private final int USE_SOFTWARE_SENSOR = 1002;

        public Compass(Context context){
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            gravitiySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            currentlyUsedSensor = USE_SOFTWARE_SENSOR;
            lowPassFilter = 0.95f;
        }

        public void start(){
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(this, gravitiySensor, SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
        }

        public void stop(){
            sensorManager.unregisterListener(this);
        }

        public void setCurrentlyUsedSensor(int i){
            currentlyUsedSensor = i;
        }

        public void setLowPassFilter(float i){
            lowPassFilter = i;
        }

        public float getLowPassFilter(){
            return lowPassFilter;
        }

        private void rotateCompass(){
            if (compassView != null){
                Animation animation = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                currentAzimuth = azimuth;
                animation.setDuration(500);
                animation.setRepeatCount(0);
                animation.setFillAfter(true);
                compassView.startAnimation(animation);
            }
        }

        private float applyLowPassFilter(float currentValue, float targetValue){
            return (lowPassFilter * currentValue + (1-lowPassFilter)*targetValue);
        }

        private float calculateAzimuth(float[] rotationMatrix){
            float orientation[] = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientation);
            float i = (float) Math.toDegrees(orientation[0]);
            i = (i +360) % 360;
            return i;
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            switch (currentlyUsedSensor) {
                case USE_HARDWARE_SENSOR: {

                    if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                        for (int i = 0; i < currentGravityValues.length; i++) {
                            currentGravityValues[i] = applyLowPassFilter(currentGravityValues[i], sensorEvent.values[i]);
                        }
                    } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                        for (int i = 0; i < currentMagneticValues.length; i++) {
                            currentMagneticValues[i] = applyLowPassFilter(currentMagneticValues[i], sensorEvent.values[i]);
                        }
                    }
                    float inclinationMatrix[] = new float[9];
                    float rotationMatrix[] = new float[9];
                    boolean validRotationResult = SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, currentGravityValues, currentMagneticValues);
                    if (validRotationResult) {
                        azimuth = calculateAzimuth(rotationMatrix);
                        rotateCompass();
                    }
                    break;
                }

                case USE_SOFTWARE_SENSOR: {

                    if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                        float[] rotationMatrix = new float[9];
                        SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
                        azimuth = calculateAzimuth(rotationMatrix);
                        rotateCompass();
                    }
                    break;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
}
