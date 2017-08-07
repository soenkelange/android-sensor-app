package de.haw_hamburg.sensorapp.recorder;

import dagger.Component;
import de.haw_hamburg.sensorapp.ApplicationComponent;
import de.haw_hamburg.sensorapp.dagger.FragmentScope;

/**
 * Created by s.lange on 17.01.17.
 */
@FragmentScope
@Component(modules = RecorderModule.class, dependencies = {ApplicationComponent.class})
public interface RecorderComponent {

    RecorderPresenter presenter();
}
