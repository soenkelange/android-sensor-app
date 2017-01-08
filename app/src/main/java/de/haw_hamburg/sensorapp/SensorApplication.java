package de.haw_hamburg.sensorapp;

import android.app.Application;

import de.haw_hamburg.sensorapp.sensor.SensorModule;

/**
 * Created by s.lange on 08.01.17.
 */
public class SensorApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .sensorModule(new SensorModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
