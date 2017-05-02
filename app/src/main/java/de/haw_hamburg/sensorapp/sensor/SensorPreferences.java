package de.haw_hamburg.sensorapp.sensor;

import android.content.Context;
import android.content.SharedPreferences;

import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Created by s.lange on 02.05.17.
 */
public class SensorPreferences {
    private final SharedPreferences sharedPreferences;

    public SensorPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SensorPreferences.class.getCanonicalName(), Context.MODE_PRIVATE);
    }

    public void setSensorEnabled(Sensor sensor, boolean enabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(String.valueOf(sensor.getType()), enabled);
        editor.apply();
    }
}
