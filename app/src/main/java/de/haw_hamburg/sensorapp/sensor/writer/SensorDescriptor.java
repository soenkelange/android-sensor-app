package de.haw_hamburg.sensorapp.sensor.writer;

import java.util.List;

/**
 * Created by s.lange on 10.05.17.
 */

public abstract class SensorDescriptor {

    public String getSensorEventValueDescription(int valueIndex) {
        return getHeaders().get(valueIndex);
    }

    public int getSensorEventValuesLength() {
        return getHeaders().size();
    }

    public abstract List<String> getHeaders();

    public abstract int getSensorType();
}
