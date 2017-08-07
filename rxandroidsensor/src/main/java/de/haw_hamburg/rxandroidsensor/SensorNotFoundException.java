package de.haw_hamburg.rxandroidsensor;

/**
 * Created by s.lange on 31.12.16.
 */
public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException(int sensorType) {
        super(String.format("Sensor %d not found on this device", sensorType));
    }
}
