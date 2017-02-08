package de.haw_hamburg.sensorapp.recorder.settings;

import dagger.Module;
import dagger.Provides;

/**
 * Created by s.lange on 08.02.17.
 */
@Module
public class RecorderSettingsModule {

    @Provides
    public RecorderSettingsPresenter provideRecorderSettingsPresenter() {
        return new RecorderSettingsPresenter();
    }
}
