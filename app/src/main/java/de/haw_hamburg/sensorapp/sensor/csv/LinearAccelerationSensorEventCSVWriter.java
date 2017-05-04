package de.haw_hamburg.sensorapp.sensor.csv;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class LinearAccelerationSensorEventCSVWriter implements SensorEventCSVWriter {
    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Acceleration force along the x axis (excluding gravity)",
                "Acceleration force along the y axis (excluding gravity)",
                "Acceleration force along the z axis (excluding gravity)");
    }

    @Override
    public Integer getType() {
        return Sensor.TYPE_LINEAR_ACCELERATION;
    }
}
