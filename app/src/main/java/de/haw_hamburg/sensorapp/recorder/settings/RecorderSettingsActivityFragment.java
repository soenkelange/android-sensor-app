package de.haw_hamburg.sensorapp.recorder.settings;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.haw_hamburg.sensorapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecorderSettingsActivityFragment extends Fragment {

    public RecorderSettingsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recorder_settings, container, false);
    }
}
