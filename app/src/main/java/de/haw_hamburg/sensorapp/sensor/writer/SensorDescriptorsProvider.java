package de.haw_hamburg.sensorapp.sensor.writer;

import java.util.ArrayList;
import java.util.List;

import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Created by s.lange on 11.05.17.
 */

public class SensorDescriptorsProvider {

    private List<SensorDescriptor> sensorDescriptors = new ArrayList<>();

    public SensorDescriptor getSensorDescriptor(Sensor sensor) {
        for (SensorDescriptor sensorDescriptor : sensorDescriptors) {
            if (sensorDescriptor.getSensorType() == sensor.getType()) {
                return sensorDescriptor;
            }
        }
        return null;
    }
}
