package de.haw_hamburg.rxandroidsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Cancellable;

/**
 * Created by s.lange on 30.12.16.
 */

public class RxSensorManager {

    private final SensorManager sensorManager;

    public RxSensorManager(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }


    public Observable<List<Sensor>> getSensorList(final int type) {
        return Observable.fromCallable(new Callable<List<Sensor>>() {
            @Override
            public List<Sensor> call() throws Exception {
                return sensorManager.getSensorList(type);
            }
        });
    }

    public boolean hasSensor(int sensorType) {
        return sensorManager.getDefaultSensor(sensorType) != null;
    }

    public Observable<SensorEvent> observeSensorEvents(final int sensorType, final int samplingPeriodUs) {
        if (!hasSensor(sensorType)) {
            return Observable.error(new SensorNotFoundException(sensorType));
        }
        return Observable.fromEmitter(new Action1<Emitter<SensorEvent>>() {
            @Override
            public void call(final Emitter<SensorEvent> sensorEventEmitter) {
                final SensorEventListener listener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        sensorEventEmitter.onNext(event);
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    }
                };
                sensorEventEmitter.setCancellation(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        sensorManager.unregisterListener(listener);
                    }
                });
                Sensor sensor = sensorManager.getDefaultSensor(sensorType);
                sensorManager.registerListener(listener, sensor, samplingPeriodUs);
            }
        }, Emitter.BackpressureMode.BUFFER);
    }
}
