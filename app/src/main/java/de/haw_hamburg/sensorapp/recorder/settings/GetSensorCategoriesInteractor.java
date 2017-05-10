package de.haw_hamburg.sensorapp.recorder.settings;

import java.util.List;

import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import de.haw_hamburg.sensorapp.sensor.SensorPreferences;
import de.haw_hamburg.sensorapp.sensor.writer.SensorDescriptorsProvider;
import rx.Observable;
import rx.functions.Func1;

import static java.util.Collections.singletonList;

/**
 * Created by s.lange on 20.02.17.
 */
public class GetSensorCategoriesInteractor {

    private final RxSensorManager sensorManager;
    private final SensorPreferences sensorPreferences;
    private final SensorDescriptorsProvider sensorDescriptorsProvider;

    public GetSensorCategoriesInteractor(RxSensorManager sensorManager, SensorPreferences sensorPreferences, SensorDescriptorsProvider sensorDescriptorsProvider) {
        this.sensorManager = sensorManager;
        this.sensorPreferences = sensorPreferences;
        this.sensorDescriptorsProvider = sensorDescriptorsProvider;
    }

    public Observable<List<SensorCategory>> execute() {
        return sensorManager.getSensorList(android.hardware.Sensor.TYPE_ALL)
                .flatMapIterable(sensors -> sensors)
                .map(s -> {
                    Sensor sensor = new Sensor(s.getName(), s.getType());
                    sensor.setEnabled(sensorPreferences.isEnabled(sensor));
                    return sensor;
                })
                .filter(sensor -> sensorDescriptorsProvider.getSensorDescriptor(sensor) != null)
                .toList()
                .map(sensors -> singletonList(new SensorCategory("All", sensors)));
    }
}
