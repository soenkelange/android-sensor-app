package de.haw_hamburg.sensorapp.sensor;

import java.util.List;

/**
 * Created by s.lange on 03.05.17.
 */
public interface SensorEventCSVWriter {

    List<String> getHeaders();

    Integer getType();
}
