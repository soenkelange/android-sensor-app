package de.haw_hamburg.sensorapp.recorder.settings;

/**
 * Created by s.lange on 20.02.17.
 */
public class Sensor {
    private final String name;
    private final int type;

    public Sensor(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
