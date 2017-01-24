package de.haw_hamburg.sensorapp.recorder;

import android.hardware.SensorManager;

import dagger.Module;
import dagger.Provides;
import de.haw_hamburg.sensorapp.sensor.GetSensorsBySensorCategoriesMapInteractor;

/**
 * Created by s.lange on 17.01.17.
 */
@Module
public class RecorderModule {

    @Provides
    public GetSensorsBySensorCategoriesMapInteractor provideGetSensorsByCategoriesInteractor(SensorManager sensorManager) {
        return new GetSensorsBySensorCategoriesMapInteractor(sensorManager);
    }
}
