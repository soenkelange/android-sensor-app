package de.haw_hamburg.sensorapp.sensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.observers.TestSubscriber;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by s.lange on 07.01.17.
 */
public class GetSensorBySensorCategoryMapTest {

    private SensorManager sensorManager;
    private GetSensorBySensorCategoryMap interactor;
    private Sensor accelerometerSensor;
    private Sensor magneticFieldSensor;
    private Sensor lightSensor;
    private Sensor ambientTemperatureSensor;

    @Before
    public void setUp() throws Exception {
        accelerometerSensor = mock(Sensor.class);
        when(accelerometerSensor.getType()).thenReturn(Sensor.TYPE_ACCELEROMETER);
        magneticFieldSensor = mock(Sensor.class);
        when(magneticFieldSensor.getType()).thenReturn(Sensor.TYPE_MAGNETIC_FIELD);
        lightSensor = mock(Sensor.class);
        when(lightSensor.getType()).thenReturn(Sensor.TYPE_LIGHT);
        ambientTemperatureSensor = mock(Sensor.class);
        when(ambientTemperatureSensor.getType()).thenReturn(Sensor.TYPE_AMBIENT_TEMPERATURE);

        sensorManager = mock(SensorManager.class);
        interactor = new GetSensorBySensorCategoryMap(sensorManager);
        interactor.setMotionSensorTypes(singletonList(Sensor.TYPE_ACCELEROMETER));
        interactor.setPositionSensorTypes(singletonList(Sensor.TYPE_MAGNETIC_FIELD));
        interactor.setEnvironmentSensorTypes(singletonList(Sensor.TYPE_AMBIENT_TEMPERATURE));
    }

    @Test
    public void construct_ShouldSetDefaultSensorCategories() {
        interactor = new GetSensorBySensorCategoryMap(sensorManager);

        assertThat(interactor.getMotionSensorTypes())
                .containsOnly(
                        Sensor.TYPE_ACCELEROMETER,
                        Sensor.TYPE_GRAVITY,
                        Sensor.TYPE_GYROSCOPE,
                        Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
                        Sensor.TYPE_LINEAR_ACCELERATION,
                        Sensor.TYPE_ROTATION_VECTOR,
                        Sensor.TYPE_SIGNIFICANT_MOTION,
                        Sensor.TYPE_STEP_COUNTER,
                        Sensor.TYPE_STEP_DETECTOR);
        assertThat(interactor.getPositionSensorTypes())
                .containsOnly(
                        Sensor.TYPE_GAME_ROTATION_VECTOR,
                        Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
                        Sensor.TYPE_MAGNETIC_FIELD,
                        Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED,
                        Sensor.TYPE_ORIENTATION,
                        Sensor.TYPE_PROXIMITY);
        assertThat(interactor.getEnvironmentSensorTypes())
                .containsOnly(
                        Sensor.TYPE_AMBIENT_TEMPERATURE,
                        Sensor.TYPE_LIGHT,
                        Sensor.TYPE_PRESSURE,
                        Sensor.TYPE_RELATIVE_HUMIDITY,
                        Sensor.TYPE_TEMPERATURE);
    }

    @Test
    public void execute_ShouldReturnObservable() {
        Observable<Map<SensorCategory, List<Sensor>>> observable = interactor.execute();

        assertThat(observable)
                .isNotNull();
    }

    @Test
    public void execute_ShouldEmitCategorizedListOfSensors() {
        List<Sensor> sensorList = Arrays.asList(
                accelerometerSensor,
                magneticFieldSensor,
                lightSensor,
                ambientTemperatureSensor);
        when(sensorManager.getSensorList(Sensor.TYPE_ALL)).thenReturn(sensorList);

        TestSubscriber<Map<SensorCategory, List<Sensor>>> subscriber = new TestSubscriber<>();
        interactor.execute().subscribe(subscriber);

        subscriber.assertValueCount(1);
        subscriber.assertCompleted();
        Map<SensorCategory, List<Sensor>> sensorsBySensorCategories = subscriber.getOnNextEvents().get(0);
        assertThat(sensorsBySensorCategories)
                .hasSize(sensorList.size())
                .containsOnlyKeys(
                        SensorCategory.MOTION_SENSORS,
                        SensorCategory.POSITION_SENSORS,
                        SensorCategory.ENVIRONMENT_SENSORS,
                        SensorCategory.UNKNOWN);
        assertThat(sensorsBySensorCategories.get(SensorCategory.MOTION_SENSORS))
                .containsOnly(accelerometerSensor);
        assertThat(sensorsBySensorCategories.get(SensorCategory.POSITION_SENSORS))
                .containsOnly(magneticFieldSensor);
        assertThat(sensorsBySensorCategories.get(SensorCategory.ENVIRONMENT_SENSORS))
                .containsOnly(ambientTemperatureSensor);
        assertThat(sensorsBySensorCategories.get(SensorCategory.UNKNOWN))
                .containsOnly(lightSensor);
    }
}