package de.haw_hamburg.sensorapp.sensor.csv;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class AccelerometerSensorEventCSVWriter implements SensorEventCSVWriter {
    @Override
    public List<String> getHeaders() {
        return  Arrays.asList(
                "Acceleration force along the x axis (including gravity)",
                "Acceleration force along the y axis (including gravity)",
                "Acceleration force along the z axis (including gravity)");
    }

    @Override
    public Integer getType() {
        return Sensor.TYPE_ACCELEROMETER;
    }
}
