package de.haw_hamburg.sensorapp;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by s.lange on 08.01.17.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Context context();
}
