package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by s.lange on 04.05.17.
 */

public class GravitySensorDescriptor extends SensorDescriptor {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Force of gravity along the x axis",
                "Force of gravity along the y axis",
                "Force of gravity along the z axis");
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_GRAVITY;
    }
}
