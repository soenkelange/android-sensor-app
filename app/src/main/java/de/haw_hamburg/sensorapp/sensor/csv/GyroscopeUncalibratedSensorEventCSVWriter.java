package de.haw_hamburg.sensorapp.sensor.csv;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class GyroscopeUncalibratedSensorEventCSVWriter implements SensorEventCSVWriter {
    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Rate of rotation (without drift compensation) around the x axis",
                "Rate of rotation (without drift compensation) around the y axis",
                "Rate of rotation (without drift compensation) around the z axis",
                "Estimated drift around the x axis",
                "Estimated drift around the y axis",
                "Estimated drift around the z axis");
    }

    @Override
    public Integer getType() {
        return Sensor.TYPE_GYROSCOPE_UNCALIBRATED;
    }
}
