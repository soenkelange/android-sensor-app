package de.haw_hamburg.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


public class CompassFragment extends Fragment{

    Compass compass;

    public CompassFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_compass, container, false);

        if (compass == null){
            compass = new Compass(getContext());
            compass.compassView = (ImageView)rootView.findViewById(R.id.compass);
        }
        return rootView;
    }

    @Override
    public void onPause(){
        compass.stop();
        super.onPause();
    }

    @Override
    public void onStart(){
        super.onStart();
        compass.start();
    }

    @Override
    public void onResume(){
        super.onResume();
        compass.start();
    }

    @Override
    public void onStop(){
        compass.stop();
        super.onStop();
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

        public Compass(Context context){
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            gravitiySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        }

        public void start(){
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(this, gravitiySensor, SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
        }

        public void stop(){
            sensorManager.unregisterListener(this);
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

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float lowPassFilter = 0.95f;
            int option = 2;
            switch (option) {
                case 1:
                    synchronized (this) {
                        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                            currentGravityValues[0] = lowPassFilter * currentGravityValues[0] + (1 - lowPassFilter) * sensorEvent.values[0];
                            currentGravityValues[1] = lowPassFilter * currentGravityValues[1] + (1 - lowPassFilter) * sensorEvent.values[1];
                            currentGravityValues[2] = lowPassFilter * currentGravityValues[2] + (1 - lowPassFilter) * sensorEvent.values[2];
                        }
                        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                            currentMagneticValues[0] = lowPassFilter * currentMagneticValues[0] + (1 - lowPassFilter) * sensorEvent.values[0];
                            currentMagneticValues[1] = lowPassFilter * currentMagneticValues[1] + (1 - lowPassFilter) * sensorEvent.values[1];
                            currentMagneticValues[2] = lowPassFilter * currentMagneticValues[2] + (1 - lowPassFilter) * sensorEvent.values[2];
                        }
                        float inclinationMatrix[] = new float[9];
                        float rotationMatrix[] = new float[9];
                        boolean validRotationResult = SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, currentGravityValues, currentMagneticValues);
                        if (validRotationResult) {
                            float orientation[] = new float[3];
                            SensorManager.getOrientation(rotationMatrix, orientation);
                            azimuth = (float) Math.toDegrees(orientation[0]);
                            azimuth = (azimuth + 360) % 360;
                            rotateCompass();
                        }
                        break;
                    }
                case 2:
                    synchronized (this){
                        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                            float[] orientation = new float[3];
                            float[] rotationMatrix = new float[9];
                            SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
                            SensorManager.getOrientation(rotationMatrix, orientation);
                            azimuth = (float)Math.toDegrees(orientation[0]);
                            azimuth = (azimuth + 360) % 360;
                            rotateCompass();
                        }
                    }
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
}
