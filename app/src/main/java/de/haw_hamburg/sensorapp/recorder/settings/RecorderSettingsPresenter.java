package de.haw_hamburg.sensorapp.recorder.settings;

import java.util.List;

import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;

/**
 * Created by s.lange on 08.02.17.
 */
public class RecorderSettingsPresenter extends AbstractPresenter<RecorderSettingsView> {
    private final GetSensorCategoriesInteractor sensorCategoriesInteractor;
    private final ToggleSensorInteractor toggleSensorInteractor;

    public RecorderSettingsPresenter(GetSensorCategoriesInteractor sensorCategoriesInteractor, ToggleSensorInteractor toggleSensorInteractor) {
        this.sensorCategoriesInteractor = sensorCategoriesInteractor;
        this.toggleSensorInteractor = toggleSensorInteractor;
    }

    public void loadSensors() {
        sensorCategoriesInteractor.execute()
                .subscribe(this::showSensorCategories);
    }

    private void showSensorCategories(List<SensorCategory> sensorCategories) {
        getView().showSensorCategories(sensorCategories);
    }

    public void toggleSensor(Sensor sensor, boolean enabled) {
        toggleSensorInteractor.execute(sensor, enabled);
    }
}
