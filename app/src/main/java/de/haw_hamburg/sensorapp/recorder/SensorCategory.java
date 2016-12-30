package de.haw_hamburg.sensorapp.recorder;

import java.util.List;

/**
 * Created by s.lange on 30.12.16.
 */
public class SensorCategory {
    private final String name;
    private final List<Sensor> sensors;

    public SensorCategory(String name, List<Sensor> sensors) {
        this.name = name;
        this.sensors = sensors;
    }

    public String getName() {
        return name;
    }

    public Sensor getSensor(int position) {
        return sensors.get(position);
    }

    public int getSensorCount() {
        return sensors.size();
    }
}
