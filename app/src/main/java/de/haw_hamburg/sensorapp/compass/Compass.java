package de.haw_hamburg.sensorapp.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;

class Compass implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gravitiySensor;
    private Sensor magneticSensor;
    private Sensor rotationVectorSensor;
    private float[] currentGravityValues = new float[3];
    private float[] currentMagneticValues = new float[3];
    private int currentlyUsedSensor;
    private float lowPassFilter;
    public final int USE_HARDWARE_SENSOR = 1001;
    public final int USE_SOFTWARE_SENSOR = 1002;
    private OnAzimuthChangedListener onAzimuthChangedListener;

    public Compass(Context context, OnAzimuthChangedListener listener) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gravitiySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        currentlyUsedSensor = USE_SOFTWARE_SENSOR;
        lowPassFilter = 0.95f;
        onAzimuthChangedListener = listener;
    }

    public interface OnAzimuthChangedListener {
        public void onAzimuthChanged(float azimuth);
    }

    public void start() {
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, gravitiySensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    public void setCurrentlyUsedSensor(int i) {
        currentlyUsedSensor = i;
    }

    public void setLowPassFilter(float i) {
        lowPassFilter = i;
    }

    public float getLowPassFilter() {
        return lowPassFilter;
    }

    private float applyLowPassFilter(float currentValue, float targetValue) {
        return (lowPassFilter * currentValue + (1 - lowPassFilter) * targetValue);
    }

    private float calculateAzimuth(float[] rotationMatrix) {
        float orientation[] = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        float i = (float) Math.toDegrees(orientation[0]);
        i = (i + 360) % 360;
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
                    onAzimuthChangedListener.onAzimuthChanged(calculateAzimuth(rotationMatrix));
                }
                break;
            }

            case USE_SOFTWARE_SENSOR: {

                if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                    float[] rotationMatrix = new float[9];
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
                    onAzimuthChangedListener.onAzimuthChanged(calculateAzimuth(rotationMatrix));
                }
                break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
