package de.haw_hamburg.sensorapp.spiritlevel;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SpiritLevel implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;
    private OnRotationChangedListener onRotationChangedListener;

    public SpiritLevel(Context context, OnRotationChangedListener listener) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        onRotationChangedListener = listener;

    }

    public void start() {
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if ( sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrix = new float[9];
            float[] orientation = new float[3];
            float[] degreeVector;
            SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
            SensorManager.getOrientation(rotationMatrix, orientation);
            degreeVector = calculateDegree(orientation);
            onRotationChangedListener.onRotationChanged(degreeVector[1], degreeVector[2]);

        }
    }

    private float[] calculateDegree(float[] orientation) {
        float[] degreeVector = new float[3];
        orientation[1] = orientation[1] * -1;
        for (int i = 0; i < orientation.length; i++) {
            float j = (float) Math.toDegrees(orientation[i]);
            j = (j + 360) % 360;
            degreeVector[i] = j;
        }
        return degreeVector;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }

    public interface OnRotationChangedListener {
        void onRotationChanged(float pitch, float roll);
    }
}
