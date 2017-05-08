package de.haw_hamburg.sensorapp.recorder;

import javax.inject.Inject;

import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;

/**
 * Created by s.lange on 29.12.16.
 */

public class RecorderPresenter extends AbstractPresenter<RecorderView> {

    private final GetEnabledSensorInteractor enabledSensorInteractor;

    @Inject
    public RecorderPresenter(GetEnabledSensorInteractor getEnabledSensorInteractor) {
        this.enabledSensorInteractor = getEnabledSensorInteractor;
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

    public void onOpenSettingsButtonClicked() {
        getView().showRecorderSettings();
    }
}
