package de.haw_hamburg.sensorapp.recorder.settings;

import dagger.Component;
import de.haw_hamburg.sensorapp.ApplicationComponent;
import de.haw_hamburg.sensorapp.dagger.FragmentScope;

/**
 * Created by s.lange on 08.02.17.
 */
@FragmentScope
@Component(modules = {RecorderSettingsModule.class}, dependencies = {ApplicationComponent.class})
public interface RecorderSettingsComponent {

    RecorderSettingsPresenter presenter();
}
