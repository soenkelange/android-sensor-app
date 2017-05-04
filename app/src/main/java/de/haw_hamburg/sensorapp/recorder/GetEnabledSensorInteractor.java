package de.haw_hamburg.sensorapp.recorder;

import java.util.List;

import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;
import de.haw_hamburg.sensorapp.sensor.SensorPreferences;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by s.lange on 02.05.17.
 */
public class GetEnabledSensorInteractor {

    private final RxSensorManager sensorManager;
    private final SensorPreferences sensorPreferences;

    public GetEnabledSensorInteractor(RxSensorManager sensorManager, SensorPreferences sensorPreferences) {
        this.sensorManager = sensorManager;
        this.sensorPreferences = sensorPreferences;
    }

    public Observable<List<Sensor>> execute() {
        return sensorManager.getSensorList(android.hardware.Sensor.TYPE_ALL)
                .flatMapIterable(new Func1<List<android.hardware.Sensor>, Iterable<android.hardware.Sensor>>() {
                    @Override
                    public Iterable<android.hardware.Sensor> call(List<android.hardware.Sensor> sensors) {
                        return sensors;
                    }
                })
                .map(s -> {
                    Sensor sensor = new Sensor(s.getName(), s.getType());
                    sensor.setEnabled(sensorPreferences.isEnabled(sensor));
                    return sensor;
                })
                .filter(sensorPreferences::isEnabled)
                .toList();
    }
}
