package de.haw_hamburg.sensorapp.recorder.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.haw_hamburg.sensorapp.BaseMvpFragment;
import de.haw_hamburg.sensorapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecorderSettingsActivityFragment extends BaseMvpFragment<RecorderSettingsPresenter, RecorderSettingsView> implements RecorderSettingsView {

    public RecorderSettingsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recorder_settings;
    }

    @Override
    public RecorderSettingsPresenter providePresenter() {
        return new RecorderSettingsPresenter();
    }
}
