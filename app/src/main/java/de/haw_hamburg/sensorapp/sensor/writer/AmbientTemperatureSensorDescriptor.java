package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class AmbientTemperatureSensorDescriptor extends SensorDescriptor {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList("Ambient air temperature");
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_AMBIENT_TEMPERATURE;
    }
}
