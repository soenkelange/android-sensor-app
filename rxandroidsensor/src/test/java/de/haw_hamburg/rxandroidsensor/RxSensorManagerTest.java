package de.haw_hamburg.rxandroidsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by s.lange on 30.12.16.
 */
public class RxSensorManagerTest {

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private RxSensorManager rxSensorManager;

    @Before
    public void setUp() throws Exception {
        sensorManager = mock(SensorManager.class);
        sensorAccelerometer = mock(Sensor.class);
        when(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)).thenReturn(sensorAccelerometer);
        rxSensorManager = new RxSensorManager(sensorManager);
    }

    @Test
    public void observeSensorEvents_ShouldReturnObservable() {
        Observable<SensorEvent> observable = rxSensorManager.observeSensorEvents(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_FASTEST);

        assertNotNull(observable);
        verifyNoMoreInteractions(sensorManager);
    }

    @Test
    public void observeSensorEvents_SubscriberSubscribed_ShouldRegisterListenerToSensorManager() {
        TestSubscriber<SensorEvent> subscriber = new TestSubscriber<>();
        rxSensorManager.observeSensorEvents(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_FASTEST)
                .subscribe(subscriber);

        verify(sensorManager).registerListener(isA(SensorEventListener.class), same(sensorAccelerometer), eq(SensorManager.SENSOR_DELAY_FASTEST));
    }

    @Test
    public void observeSensorEvents_SubscriberSubscribed_ShouldEmitSensorEvents() {
        final SensorEvent expectedSensorEvent = mock(SensorEvent.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                SensorEventListener listener = (SensorEventListener) invocation.getArguments()[0];
                listener.onSensorChanged(expectedSensorEvent);
                return null;
            }
        }).when(sensorManager).registerListener(isA(SensorEventListener.class), same(sensorAccelerometer), eq(SensorManager.SENSOR_DELAY_FASTEST));

        TestSubscriber<SensorEvent> subscriber = new TestSubscriber<>();
        rxSensorManager.observeSensorEvents(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_FASTEST)
                .subscribe(subscriber);

        subscriber.assertValue(expectedSensorEvent);
    }

    @Test
    public void observeSensorEvents_Unsubscribe_ShouldUnregisterRegisterdListener() {
        TestSubscriber<SensorEvent> subscriber = new TestSubscriber<>();
        rxSensorManager.observeSensorEvents(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_FASTEST)
                .subscribe(subscriber);
        subscriber.unsubscribe();

        ArgumentCaptor<SensorEventListener> sensorEventListenerArgumentCaptor = ArgumentCaptor.forClass(SensorEventListener.class);
        verify(sensorManager).registerListener(sensorEventListenerArgumentCaptor.capture(), same(sensorAccelerometer), eq(SensorManager.SENSOR_DELAY_FASTEST));
        SensorEventListener registeredListener = sensorEventListenerArgumentCaptor.getValue();
        verify(sensorManager).unregisterListener(same(registeredListener));
    }
}