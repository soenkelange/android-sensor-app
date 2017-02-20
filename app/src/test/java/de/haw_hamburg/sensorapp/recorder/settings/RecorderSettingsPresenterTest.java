package de.haw_hamburg.sensorapp.recorder.settings;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by s.lange on 20.02.17.
 */
public class RecorderSettingsPresenterTest {

    private GetSensorCategoriesInteractor sensorCategoriesInteractor;
    private ToggleSensorInteractor toggleSensorInteractor;
    private RecorderSettingsView recorderSettingsView;
    private RecorderSettingsPresenter recorderSettingsPresenter;

    @Before
    public void setUp() throws Exception {
        sensorCategoriesInteractor = mock(GetSensorCategoriesInteractor.class);
        toggleSensorInteractor = mock(ToggleSensorInteractor.class);
        recorderSettingsView = mock(RecorderSettingsView.class);
        recorderSettingsPresenter = new RecorderSettingsPresenter(sensorCategoriesInteractor, toggleSensorInteractor);
        recorderSettingsPresenter.attachView(recorderSettingsView);
    }

    @Test
    public void shouldShowSensorCategories() {
        List<SensorCategory> sensorCategories = new ArrayList<>();
        when(sensorCategoriesInteractor.execute()).thenReturn(Observable.just(sensorCategories));

        recorderSettingsPresenter.loadSensors();

        verify(recorderSettingsView).showSensorCategories(sensorCategories);
    }

    @Test
    public void shouldToggleSensor() {
        Sensor sensor = new Sensor();
        boolean enabled = true;
        recorderSettingsPresenter.toggleSensor(sensor, enabled);

        verify(toggleSensorInteractor).execute(sensor, enabled);
    }
}
