package de.haw_hamburg.sensorapp.recorder.settings;

import java.util.List;

import de.haw_hamburg.sensorapp.mvp.View;

/**
 * Created by s.lange on 08.02.17.
 */
public interface RecorderSettingsView extends View {
    void showSensorCategories(List<SensorCategory> sensorCategories);
}
