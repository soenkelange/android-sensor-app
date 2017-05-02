package de.haw_hamburg.sensorapp.recorder.settings;

/**
 * Created by s.lange on 20.02.17.
 */
public class Sensor {
    private final String name;
    private final int type;
    private boolean enabled;

    public Sensor(String name, int type) {
        this.name = name;
        this.type = type;
        this.enabled = false;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
