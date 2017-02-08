package de.haw_hamburg.sensorapp.recorder.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.haw_hamburg.sensorapp.BaseMvpFragment;
import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.SensorApplication;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecorderSettingsFragment extends BaseMvpFragment<RecorderSettingsPresenter, RecorderSettingsView> implements RecorderSettingsView {

    private RecorderSettingsComponent recorderSettingsComponent;

    public RecorderSettingsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SensorApplication sensorApplication = (SensorApplication) getActivity().getApplication();
        recorderSettingsComponent = DaggerRecorderSettingsComponent.builder()
                .applicationComponent(sensorApplication.getApplicationComponent())
                .build();
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
        return recorderSettingsComponent.presenter();
    }
}
