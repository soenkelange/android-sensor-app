package de.haw_hamburg.sensorapp;

import android.content.Context;
import android.hardware.SensorManager;

import javax.inject.Singleton;

import dagger.Component;
import de.haw_hamburg.sensorapp.sensor.SensorModule;

/**
 * Created by s.lange on 08.01.17.
 */
@Singleton
@Component(modules = {ApplicationModule.class, SensorModule.class})
public interface ApplicationComponent {

    Context context();

    SensorManager getSensorManager();
}
