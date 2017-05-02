package de.haw_hamburg.sensorapp.recorder.settings;

import de.haw_hamburg.sensorapp.sensor.SensorPreferences;

/**
 * Created by s.lange on 20.02.17.
 */
public class ToggleSensorInteractor {
    private final SensorPreferences sensorPreferences;

    public ToggleSensorInteractor(SensorPreferences sensorPreferences) {
        this.sensorPreferences = sensorPreferences;
    }

    public void execute(Sensor sensor, boolean enabled) {
        sensorPreferences.setSensorEnabled(sensor, enabled);
    }
}
