package de.haw_hamburg.sensorapp.sensor.csv;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class GyroscopeSensorEventCSVWriter implements SensorEventCSVWriter {
    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Rate of rotation around the x axis",
                "Rate of rotation around the y axis",
                "Rate of rotation around the z axis");
    }
}
