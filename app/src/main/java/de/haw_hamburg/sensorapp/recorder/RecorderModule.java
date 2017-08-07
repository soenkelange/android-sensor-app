package de.haw_hamburg.sensorapp.recorder;

import dagger.Module;
import dagger.Provides;
import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import de.haw_hamburg.sensorapp.sensor.SensorEventListenerInteractor;
import de.haw_hamburg.sensorapp.sensor.SensorPreferences;
import de.haw_hamburg.sensorapp.sensor.writer.SensorDescriptorsProvider;

/**
 * Created by s.lange on 17.01.17.
 */
@Module
public class RecorderModule {

    @Provides
    public GetEnabledSensorInteractor provideGetEnabledSensorInteractor(
            RxSensorManager rxSensorManager,
            SensorPreferences sensorPreferences,
            SensorDescriptorsProvider sensorDescriptorsProvider) {
        return new GetEnabledSensorInteractor(rxSensorManager, sensorPreferences, sensorDescriptorsProvider);
    }

    @Provides
    public SensorEventListenerInteractor provideSensorEventListenerInteractor(RxSensorManager rxSensorManager) {
        return new SensorEventListenerInteractor(rxSensorManager);
    }
}
