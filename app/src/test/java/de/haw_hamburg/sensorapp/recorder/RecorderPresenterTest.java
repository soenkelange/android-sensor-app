package de.haw_hamburg.sensorapp.recorder;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.haw_hamburg.sensorapp.recorder.settings.Sensor;
import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by s.lange on 08.02.17.
 */
public class RecorderPresenterTest {

    private GetEnabledSensorInteractor getEnabledSensorsInteractor;
    private RecorderView view;
    private RecorderPresenter recorderPresenter;

    @Before
    public void setUp() throws Exception {
        getEnabledSensorsInteractor = mock(GetEnabledSensorInteractor.class);
        view = mock(RecorderView.class);
        recorderPresenter = new RecorderPresenter(getEnabledSensorsInteractor);
        recorderPresenter.attachView(view);
    }

    @Test
    public void whenNoSensorsAreEnabled_ShouldShowNoSensorsEnabled() {
        when(getEnabledSensorsInteractor.execute()).thenReturn(Observable.just(Collections.emptyList()));

        recorderPresenter.initialize();

        verify(view).showNoSensorsEnabledHint();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenSensorAreEnabled_ShouldShowRecorderControls() {
        Sensor accelerometer = new Sensor("Accelerometer", android.hardware.Sensor.TYPE_ACCELEROMETER);
        List<Sensor> sensorList = Arrays.asList(accelerometer);
        when(getEnabledSensorsInteractor.execute()).thenReturn(Observable.just(sensorList));

        recorderPresenter.initialize();

        verify(view).addSensorLineChart(accelerometer);
        verify(view).showRecorderControls();
    }

    @Test
    public void whenSettingsMenuItemClicked_ShouldOpenRecorderSettings() {
        recorderPresenter.onSettingsMenuItemClicked();

        verify(view).showRecorderSettings();
    }

    @Test
    public void whenOpenSettingsButtonClicked_ShoulOpenRecorderSettings() {
        recorderPresenter.onOpenSettingsButtonClicked();

        verify(view).showRecorderSettings();
    }
}
