package de.haw_hamburg.sensorapp.recorder.settings;

import java.util.List;

/**
 * Created by s.lange on 20.02.17.
 */

public class SensorCategory {
    private String name;
    private List<Sensor> sensors;

    public SensorCategory(String name, List<Sensor> sensors) {
        this.name = name;
        this.sensors = sensors;
    }

    public String getName() {
        return name;
    }

    public Sensor getSensor(int sensorPosition) {
        return sensors.get(sensorPosition);
    }

    public int getSensorsCount() {
        return sensors.size();
    }
}
