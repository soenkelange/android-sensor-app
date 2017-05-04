package de.haw_hamburg.sensorapp.sensor.csv;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;

/**
 * Created by s.lange on 04.05.17.
 */

public class MagneticFieldUncalibratedSensorEventCSVWriter implements SensorEventCSVWriter {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Geomagnetic field strength (without hard iron calibration) along the x axis",
                "Geomagnetic field strength (without hard iron calibration) along the y axis",
                "Geomagnetic field strength (without hard iron calibration) along the z axis",
                "Iron bias estimation along the x axis",
                "Iron bias estimation along the y axis",
                "Iron bias estimation along the z axis");
    }

    @Override
    public Integer getType() {
        return Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED;
    }
}
