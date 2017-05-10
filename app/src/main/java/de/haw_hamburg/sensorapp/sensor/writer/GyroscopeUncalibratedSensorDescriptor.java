package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class GyroscopeUncalibratedSensorDescriptor extends SensorDescriptor {

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
    public int getSensorType() {
        return Sensor.TYPE_GYROSCOPE_UNCALIBRATED;
    }
}
