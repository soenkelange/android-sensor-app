package de.haw_hamburg.sensorapp.recorder;

import android.hardware.SensorEvent;
import android.net.Uri;

import de.haw_hamburg.sensorapp.mvp.View;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Created by s.lange on 29.12.16.
 */
public interface RecorderView extends View {

    void showRecorderControls();

    void showNoSensorsEnabledHint();

    void showRecorderSettings();

    void showStartButton();

    void showStopButton();

    void addSensorLineChart(Sensor sensor);

    void addSensorEvent(SensorEvent sensorEvent);

    void exportFile(Uri uri);
}
