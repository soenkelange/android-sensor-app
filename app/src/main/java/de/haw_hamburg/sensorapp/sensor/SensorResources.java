package de.haw_hamburg.sensorapp.sensor;

import android.content.Context;
import android.hardware.Sensor;

import java.util.HashMap;
import java.util.Map;

import de.haw_hamburg.sensorapp.R;

/**
 * Created by s.lange on 24.01.17.
 */

public class SensorResources {

    private static final Map<SensorCategory, Integer> sensorCategoryToStringResource = new HashMap<>();
    private static final Map<Integer, Integer> sensorTypeToStringResource = new HashMap<>();

    static {
        sensorCategoryToStringResource.put(SensorCategory.MOTION_SENSORS, R.string.sensorCategory_motion);
        sensorCategoryToStringResource.put(SensorCategory.ENVIRONMENT_SENSORS, R.string.sensorCategory_environment);
        sensorCategoryToStringResource.put(SensorCategory.POSITION_SENSORS, R.string.sensorCategory_position);
        sensorCategoryToStringResource.put(SensorCategory.UNKNOWN, R.string.sensorCategory_unknown);

        sensorTypeToStringResource.put(Sensor.TYPE_ACCELEROMETER, R.string.sensor_accelerometer);
        sensorTypeToStringResource.put(Sensor.TYPE_GRAVITY, R.string.sensor_gravity);
        sensorTypeToStringResource.put(Sensor.TYPE_GYROSCOPE, R.string.sensor_gyroscope);
        sensorTypeToStringResource.put(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, R.string.sensor_gyroscopeUncalibrated);
        sensorTypeToStringResource.put(Sensor.TYPE_LINEAR_ACCELERATION, R.string.sensor_linearAcceleration);
        sensorTypeToStringResource.put(Sensor.TYPE_ROTATION_VECTOR, R.string.sensor_rotationVector);
        sensorTypeToStringResource.put(Sensor.TYPE_SIGNIFICANT_MOTION, R.string.sensor_significantMotion);
        sensorTypeToStringResource.put(Sensor.TYPE_STEP_COUNTER, R.string.sensor_significantMotion);
        sensorTypeToStringResource.put(Sensor.TYPE_STEP_DETECTOR, R.string.sensor_stepDetector);
        sensorTypeToStringResource.put(Sensor.TYPE_GAME_ROTATION_VECTOR, R.string.sensor_gameRotationVector);
        sensorTypeToStringResource.put(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, R.string.sensor_geomagneticRotationVector);
        sensorTypeToStringResource.put(Sensor.TYPE_MAGNETIC_FIELD, R.string.sensor_magneticField);
        sensorTypeToStringResource.put(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, R.string.sensor_magneticFieldUncalibrated);
        sensorTypeToStringResource.put(Sensor.TYPE_ORIENTATION, R.string.sensor_orientation);
        sensorTypeToStringResource.put(Sensor.TYPE_PROXIMITY, R.string.sensor_proximity);
        sensorTypeToStringResource.put(Sensor.TYPE_AMBIENT_TEMPERATURE, R.string.sensor_ambientTemperature);
        sensorTypeToStringResource.put(Sensor.TYPE_LIGHT, R.string.sensor_light);
        sensorTypeToStringResource.put(Sensor.TYPE_PRESSURE, R.string.sensor_pressure);
        sensorTypeToStringResource.put(Sensor.TYPE_RELATIVE_HUMIDITY, R.string.sensor_relativeHumidity);
        sensorTypeToStringResource.put(Sensor.TYPE_TEMPERATURE, R.string.sensor_temperature);
    }

    private Context context;

    public SensorResources(Context context) {
        this.context = context;
    }

    public String getSensorCategoryString(SensorCategory sensorCategory) {
        int resourceId = sensorCategoryToStringResource.get(sensorCategory);
        return context.getResources().getString(resourceId);
    }

    public String getSensorTypeString(int sensorType) {
        Integer resourceId = sensorTypeToStringResource.get(sensorType);
        return context.getResources().getString(resourceId);
    }
}
