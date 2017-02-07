package de.haw_hamburg.sensorapp.sensor;

import android.content.Context;
import android.hardware.SensorManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.haw_hamburg.rxandroidsensor.RxSensorManager;

/**
 * Created by s.lange on 17.01.17.
 */
@Module
public class SensorModule {

    @Provides
    @Singleton
    public SensorManager provideSensorManager(Context context) {
        return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Provides
    @Singleton
    public RxSensorManager providesRxSensorManager(SensorManager sensorManager) {
        return new RxSensorManager(sensorManager);
    }
}