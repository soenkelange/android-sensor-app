package de.haw_hamburg.sensorapp.sensor.csv;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class LightSensorEventCSVWriter implements SensorEventCSVWriter {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList("Illuminance");
    }

    @Override
    public Integer getType() {
        return Sensor.TYPE_LIGHT;
    }
}
