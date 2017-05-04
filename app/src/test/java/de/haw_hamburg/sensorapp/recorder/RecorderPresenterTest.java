package de.haw_hamburg.sensorapp.recorder;

import org.junit.Before;
import org.junit.Test;

import de.haw_hamburg.rxandroidsensor.RxSensorManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by s.lange on 08.02.17.
 */
public class RecorderPresenterTest {

    private RecorderView view;
    private RecorderPresenter recorderPresenter;

    @Before
    public void setUp() throws Exception {
        GetEnabledSensorInteractor getEnabledSensorsInteractor = mock(GetEnabledSensorInteractor.class);
        RxSensorManager rxSensorManager = mock(RxSensorManager.class);
        view = mock(RecorderView.class);
        recorderPresenter = new RecorderPresenter(getEnabledSensorsInteractor, rxSensorManager);
        recorderPresenter.attachView(view);
    }

    @Test
    public void shouldOpenRecorderSettings() {
        recorderPresenter.onSettingsClicked();

        verify(view).showRecorderSettings();
    }
}
