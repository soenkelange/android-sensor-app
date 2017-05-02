package de.haw_hamburg.sensorapp.recorder.settings;

import dagger.Module;
import dagger.Provides;
import de.haw_hamburg.rxandroidsensor.RxSensorManager;

/**
 * Created by s.lange on 08.02.17.
 */
@Module
public class RecorderSettingsModule {

    @Provides
    public GetSensorCategoriesInteractor provideGetSensorCategoriesInteractor(RxSensorManager rxSensorManager) {
        return new GetSensorCategoriesInteractor(rxSensorManager);
    }

    @Provides
    public ToggleSensorInteractor provideToggleSensorInteractor() {
        return new ToggleSensorInteractor();
    }

    @Provides
    public RecorderSettingsPresenter provideRecorderSettingsPresenter(GetSensorCategoriesInteractor interactor, ToggleSensorInteractor toggleSensorInteractor) {
        return new RecorderSettingsPresenter(interactor, toggleSensorInteractor);
    }
}
