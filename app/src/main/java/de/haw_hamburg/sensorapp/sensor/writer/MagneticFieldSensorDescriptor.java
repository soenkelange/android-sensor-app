package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by s.lange on 04.05.17.
 */

public class MagneticFieldSensorDescriptor extends SensorDescriptor {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Geomagnetic field strength along the x axis",
                "Geomagnetic field strength along the y axis",
                "Geomagnetic field strength along the z axis");
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_MAGNETIC_FIELD;
    }
}
