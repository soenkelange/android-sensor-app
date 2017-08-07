package de.haw_hamburg.sensorapp.recorder.settings;

import dagger.Module;
import dagger.Provides;
import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import de.haw_hamburg.sensorapp.sensor.SensorPreferences;
import de.haw_hamburg.sensorapp.sensor.writer.SensorDescriptorsProvider;

/**
 * Created by s.lange on 08.02.17.
 */
@Module
public class RecorderSettingsModule {

    @Provides
    public GetSensorCategoriesInteractor provideGetSensorCategoriesInteractor(
            RxSensorManager rxSensorManager,
            SensorPreferences sensorPreferences,
            SensorDescriptorsProvider sensorDescriptorsProvider) {
        return new GetSensorCategoriesInteractor(rxSensorManager, sensorPreferences, sensorDescriptorsProvider);
    }

    @Provides
    public ToggleSensorInteractor provideToggleSensorInteractor(SensorPreferences sensorPreferences) {
        return new ToggleSensorInteractor(sensorPreferences);
    }

    @Provides
    public RecorderSettingsPresenter provideRecorderSettingsPresenter(GetSensorCategoriesInteractor interactor, ToggleSensorInteractor toggleSensorInteractor) {
        return new RecorderSettingsPresenter(interactor, toggleSensorInteractor);
    }
}
