package de.haw_hamburg.sensorapp.recorder;

import javax.inject.Inject;

import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;

/**
 * Created by s.lange on 29.12.16.
 */

public class RecorderPresenter extends AbstractPresenter<RecorderView> {

    private final GetEnabledSensorInteractor enabledSensorInteractor;
    private final RxSensorManager rxSensorManager;

    @Inject
    public RecorderPresenter(GetEnabledSensorInteractor getEnabledSensorInteractor, RxSensorManager rxSensorManager) {
        this.enabledSensorInteractor = getEnabledSensorInteractor;
        this.rxSensorManager = rxSensorManager;
    }


    public void onSettingsClicked() {
        getView().showRecorderSettings();
    }
}
