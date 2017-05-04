package de.haw_hamburg.sensorapp.recorder;

import org.junit.Before;
import org.junit.Test;

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
        view = mock(RecorderView.class);
        recorderPresenter = new RecorderPresenter(getEnabledSensorsInteractor);
        recorderPresenter.attachView(view);
    }

    @Test
    public void shouldOpenRecorderSettings() {
        recorderPresenter.onSettingsClicked();

        verify(view).showRecorderSettings();
    }
}
