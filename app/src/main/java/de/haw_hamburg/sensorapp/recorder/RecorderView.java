package de.haw_hamburg.sensorapp.recorder;

import de.haw_hamburg.sensorapp.mvp.View;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Created by s.lange on 29.12.16.
 */
public interface RecorderView extends View {

    void showRecorderControls();

    void showNoSensorsEnabledHint();

    void showRecorderSettings();

    void addSensorLineChart(Sensor sensor);
}
