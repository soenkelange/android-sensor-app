package de.haw_hamburg.sensorapp.recorder;

import android.net.Uri;

import de.haw_hamburg.sensorapp.mvp.View;

/**
 * Created by s.lange on 29.12.16.
 */
public interface RecorderView extends View {

    void showStartButton();

    void hideStartButton();

    void showStopButton();

    void hideStopButton();

    void showStopRecordingDialog();

    void showRecorderSettings();

    void exportCSV(Uri uri);
}
