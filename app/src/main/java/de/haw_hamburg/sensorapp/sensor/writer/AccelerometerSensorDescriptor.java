package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by s.lange on 04.05.17.
 */

public class AccelerometerSensorDescriptor extends SensorDescriptor {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Acceleration force along the x axis (including gravity)",
                "Acceleration force along the y axis (including gravity)",
                "Acceleration force along the z axis (including gravity)");
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_ACCELEROMETER;
    }
}
