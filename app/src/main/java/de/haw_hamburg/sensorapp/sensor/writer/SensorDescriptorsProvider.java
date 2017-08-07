package de.haw_hamburg.sensorapp.sensor.writer;

import java.util.ArrayList;
import java.util.List;

import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Created by s.lange on 11.05.17.
 */

public class SensorDescriptorsProvider {

    private List<SensorDescriptor> sensorDescriptors = new ArrayList<>();

    public SensorDescriptorsProvider() {
        sensorDescriptors.add(new AccelerometerSensorDescriptor());
        sensorDescriptors.add(new AmbientTemperatureSensorDescriptor());
        sensorDescriptors.add(new GameRotationVectorSensorDescriptor());
        sensorDescriptors.add(new GeomagneticRotationVectorSensorDescriptor());
        sensorDescriptors.add(new GravitySensorDescriptor());
        sensorDescriptors.add(new GyroscopeSensorDescriptor());
        sensorDescriptors.add(new GyroscopeUncalibratedSensorDescriptor());
        sensorDescriptors.add(new LightSensorDescriptor());
        sensorDescriptors.add(new LinearAccelerationSensorDescriptor());
        sensorDescriptors.add(new MagneticFieldSensorDescriptor());
        sensorDescriptors.add(new OrientationSensorDescriptor());
        sensorDescriptors.add(new PresureSensorDescriptor());
        sensorDescriptors.add(new ProximitySensorDescriptor());
        sensorDescriptors.add(new RelativeHumiditySensorDescriptor());
        sensorDescriptors.add(new RotationVectorSensorDescriptor());
    }

    public SensorDescriptor getSensorDescriptor(Sensor sensor) {
        for (SensorDescriptor sensorDescriptor : sensorDescriptors) {
            if (sensorDescriptor.getSensorType() == sensor.getType()) {
                return sensorDescriptor;
            }
        }
        return null;
    }
}
