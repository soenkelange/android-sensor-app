package de.haw_hamburg.sensorapp.sensor;

import android.hardware.Sensor;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by s.lange on 07.01.17.
 */
public enum SensorCategory {
    UNKNOWN,
    MOTION_SENSORS(asList(Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GRAVITY, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_GYROSCOPE_UNCALIBRATED, Sensor.TYPE_LINEAR_ACCELERATION, Sensor.TYPE_ROTATION_VECTOR, Sensor.TYPE_SIGNIFICANT_MOTION, Sensor.TYPE_STEP_COUNTER, Sensor.TYPE_STEP_DETECTOR)),
    POSITION_SENSORS(asList(Sensor.TYPE_GAME_ROTATION_VECTOR, Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, Sensor.TYPE_ORIENTATION, Sensor.TYPE_PROXIMITY)),
    ENVIRONMENT_SENSORS(asList(Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_LIGHT, Sensor.TYPE_PRESSURE, Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_TEMPERATURE));

    private List<Integer> sensors;

    SensorCategory(List<Integer> sensors) {
        this.sensors = sensors;
    }

    SensorCategory() {
        this(Collections.emptyList());
    }

    public boolean containsSensor(int sensorType) {
        return sensors.contains(sensorType);
    }
}
