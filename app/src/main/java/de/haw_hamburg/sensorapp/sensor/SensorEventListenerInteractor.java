package de.haw_hamburg.sensorapp.sensor;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;
import rx.Observable;

/**
 * Created by s.lange on 09.05.17.
 */

public class SensorEventListenerInteractor {
    private final RxSensorManager rxSensorManager;

    public SensorEventListenerInteractor(RxSensorManager rxSensorManager) {
        this.rxSensorManager = rxSensorManager;
    }

    public Observable<SensorEvent> execute(Sensor sensor) {
        return rxSensorManager.observeSensorEvents(sensor.getType(), SensorManager.SENSOR_DELAY_FASTEST);
    }
}
