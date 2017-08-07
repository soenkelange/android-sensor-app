package de.haw_hamburg.sensorapp.sensor.csv;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class GeomagneticRotationVectorSensorEventCSVWriter implements SensorEventCSVWriter {
    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Rotation vector component along the x axis (x * sin(θ/2))",
                "Rotation vector component along the y axis (y * sin(θ/2))",
                "Rotation vector component along the z axis (z * sin(θ/2))");
    }

    @Override
    public Integer getType() {
        return Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR;
    }
}
