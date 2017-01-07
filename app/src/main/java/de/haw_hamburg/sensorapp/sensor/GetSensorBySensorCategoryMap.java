package de.haw_hamburg.sensorapp.sensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by s.lange on 07.01.17.
 */
public class GetSensorBySensorCategoryMap {

    private static final List<Integer> MOTION_SENSOR_TYPES = Arrays.asList(
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_ROTATION_VECTOR,
            Sensor.TYPE_SIGNIFICANT_MOTION,
            Sensor.TYPE_STEP_COUNTER,
            Sensor.TYPE_STEP_DETECTOR);
    private static final List<Integer> POSITION_SENSOR_TYPES = Arrays.asList(
            Sensor.TYPE_GAME_ROTATION_VECTOR,
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
            Sensor.TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED,
            Sensor.TYPE_ORIENTATION,
            Sensor.TYPE_PROXIMITY);
    private static final List<Integer> ENVIRONMENT_SENSOR_TYPES = Arrays.asList(
            Sensor.TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_LIGHT,
            Sensor.TYPE_PRESSURE,
            Sensor.TYPE_RELATIVE_HUMIDITY,
            Sensor.TYPE_TEMPERATURE);
    private final SensorManager sensorManager;
    private List<Integer> motionSensorTypes = MOTION_SENSOR_TYPES;
    private List<Integer> positionSensorTypes = POSITION_SENSOR_TYPES;
    private List<Integer> environmentSensorTypes = ENVIRONMENT_SENSOR_TYPES;

    public GetSensorBySensorCategoryMap(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public Observable<Map<SensorCategory, List<Sensor>>> execute() {
        return Observable.fromCallable(this::getSensorsBySensorCategories);
    }

    private Map<SensorCategory, List<Sensor>> getSensorsBySensorCategories() {
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Map<SensorCategory, List<Sensor>> sensorsBySensorCategory = new HashMap<>();
        for (Sensor sensor : sensors) {
            SensorCategory sensorCategory = SensorCategory.UNKNOWN;
            if (motionSensorTypes.contains(sensor.getType())) {
                sensorCategory = SensorCategory.MOTION_SENSORS;
            } else if (positionSensorTypes.contains(sensor.getType())) {
                sensorCategory = SensorCategory.POSITION_SENSORS;
            } else if (environmentSensorTypes.contains(sensor.getType())) {
                sensorCategory = SensorCategory.ENVIRONMENT_SENSORS;
            }
            List<Sensor> sensorList = sensorsBySensorCategory.get(sensorCategory);
            if (sensorList == null) {
                sensorList = new ArrayList<>();
                sensorsBySensorCategory.put(sensorCategory, sensorList);
            }
            sensorList.add(sensor);
        }
        return sensorsBySensorCategory;
    }

    public List<Integer> getMotionSensorTypes() {
        return motionSensorTypes;
    }

    public void setMotionSensorTypes(List<Integer> motionSensorTypes) {
        this.motionSensorTypes = motionSensorTypes;
    }

    public List<Integer> getPositionSensorTypes() {
        return positionSensorTypes;
    }

    public void setPositionSensorTypes(List<Integer> positionSensorTypes) {
        this.positionSensorTypes = positionSensorTypes;
    }

    public List<Integer> getEnvironmentSensorTypes() {
        return environmentSensorTypes;
    }

    public void setEnvironmentSensorTypes(List<Integer> environmentSensorTypes) {
        this.environmentSensorTypes = environmentSensorTypes;
    }
}
