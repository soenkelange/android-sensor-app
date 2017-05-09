package de.haw_hamburg.sensorapp.recorder;

import android.hardware.SensorEvent;

import javax.inject.Inject;

import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;
import de.haw_hamburg.sensorapp.sensor.SensorEventListenerInteractor;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by s.lange on 29.12.16.
 */

public class RecorderPresenter extends AbstractPresenter<RecorderView> {

    private final GetEnabledSensorInteractor enabledSensorInteractor;
    private final SensorEventListenerInteractor sensorEventListenerInteractor;
    private Subscription subscription;
    private boolean recording;

    @Inject
    public RecorderPresenter(GetEnabledSensorInteractor getEnabledSensorInteractor, SensorEventListenerInteractor sensorEventListenerInteractor) {
        this.enabledSensorInteractor = getEnabledSensorInteractor;
        this.sensorEventListenerInteractor = sensorEventListenerInteractor;
    }


    public void initialize() {
        getView().showNoSensorsEnabledHint();
        enabledSensorInteractor.execute()
                .flatMapIterable(sensors -> sensors)
                .subscribe(sensor -> {
                    getView().addSensorLineChart(sensor);
                    getView().showRecorderControls();
                });
    }

    public void onSettingsMenuItemClicked() {
        getView().showRecorderSettings();
    }

    public void onControlButtonClicked() {
        if (isRecording()) {
            stopRecording();
            return;
        }
        startRecording();
    }

    private void startRecording() {
        recording = true;
        subscription = enabledSensorInteractor.execute()
                .flatMapIterable(sensors -> sensors)
                .flatMap(new Func1<Sensor, Observable<SensorEvent>>() {
                    @Override
                    public Observable<SensorEvent> call(Sensor sensor) {
                        return sensorEventListenerInteractor.execute(sensor);
                    }
                })
                .subscribe(new Action1<SensorEvent>() {
                    @Override
                    public void call(SensorEvent sensorEvent) {
                        getView().addSensorEvent(sensorEvent);
                    }
                });
    }

    private void stopRecording() {
        recording = false;
        subscription.unsubscribe();
    }

    public void onOpenSettingsButtonClicked() {
        getView().showRecorderSettings();
    }

    public boolean isRecording() {
        return recording;
    }
}
