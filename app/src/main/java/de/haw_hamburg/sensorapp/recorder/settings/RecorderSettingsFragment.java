package de.haw_hamburg.sensorapp.recorder.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import de.haw_hamburg.sensorapp.BaseMvpFragment;
import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.SensorApplication;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecorderSettingsFragment extends BaseMvpFragment<RecorderSettingsPresenter, RecorderSettingsView> implements RecorderSettingsView {

    private RecorderSettingsComponent recorderSettingsComponent;
    private RecyclerView sensorsRecyclerView;
    private SensorsRecyclerViewAdapter sensorsRecyclerViewAdapter;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorsRecyclerView = (RecyclerView) view.findViewById(R.id.sensorsRecyclerView);
        sensorsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sensorsRecyclerViewAdapter = new SensorsRecyclerViewAdapter();
        sensorsRecyclerViewAdapter.setListener(this::toggleSensor);
        sensorsRecyclerView.setAdapter(sensorsRecyclerViewAdapter);
        sensorsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING){
                    sensorsRecyclerViewAdapter.setIsScrolling(true);
                }
                else{
                    sensorsRecyclerViewAdapter.setIsScrolling(false);
                }
            }
        });
        getPresenter().loadSensors();
    }

    private void toggleSensor(Sensor sensor, boolean isChecked) {
        getPresenter().toggleSensor(sensor, isChecked);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recorder_settings;
    }

    @Override
    public RecorderSettingsPresenter providePresenter() {
        return recorderSettingsComponent.presenter();
    }

    @Override
    public void showSensorCategories(List<SensorCategory> sensorCategories) {
        sensorsRecyclerViewAdapter.setSensorCategories(sensorCategories);
    }
}
