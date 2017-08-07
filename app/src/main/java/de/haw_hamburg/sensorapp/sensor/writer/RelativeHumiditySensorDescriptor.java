package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by s.lange on 04.05.17.
 */

public class RelativeHumiditySensorDescriptor extends SensorDescriptor {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList("Ambient relative humidity");
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_RELATIVE_HUMIDITY;
    }
}
