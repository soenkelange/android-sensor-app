package de.haw_hamburg.sensorapp.recorder.settings;

import java.util.List;

import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import rx.Observable;

import static java.util.Collections.singletonList;

/**
 * Created by s.lange on 20.02.17.
 */
public class GetSensorCategoriesInteractor {

    private final RxSensorManager sensorManager;

    public GetSensorCategoriesInteractor(RxSensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public Observable<List<SensorCategory>> execute() {
        return sensorManager.getSensorList(android.hardware.Sensor.TYPE_ALL)
                .flatMapIterable(sensors -> sensors)
                .map(sensor -> new Sensor(sensor.getName(), sensor.getType()))
                .toList()
                .map(sensors -> singletonList(new SensorCategory("All", sensors)));
    }
}
