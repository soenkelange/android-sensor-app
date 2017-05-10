package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by s.lange on 04.05.17.
 */

public class GyroscopeSensorDescriptor extends SensorDescriptor {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Rate of rotation around the x axis",
                "Rate of rotation around the y axis",
                "Rate of rotation around the z axis");
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_GYROSCOPE;
    }
}
