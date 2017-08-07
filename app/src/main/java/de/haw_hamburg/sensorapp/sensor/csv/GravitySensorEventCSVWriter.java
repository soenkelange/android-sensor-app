package de.haw_hamburg.sensorapp.sensor.csv;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class GravitySensorEventCSVWriter implements SensorEventCSVWriter {
    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Force of gravity along the x axis",
                "Force of gravity along the y axis",
                "Force of gravity along the z axis");
    }

    @Override
    public Integer getType() {
        return Sensor.TYPE_GRAVITY;
    }
}
