package de.haw_hamburg.sensorapp.recorder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.SensorApplication;
import de.haw_hamburg.sensorapp.navigation.BaseNavigationFragment;

/**
 * Use the {@link RecorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecorderFragment extends BaseNavigationFragment<RecorderPresenter, RecorderView> implements RecorderView {

    private RecyclerView sensorsRecyclerView;
    private RecorderComponent recorderComponent;

    public RecorderFragment() {
        // Required empty public constructor
    }

    public static RecorderFragment newInstance() {
        return new RecorderFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SensorApplication sensorApplication = (SensorApplication) getActivity().getApplication();
        recorderComponent = DaggerRecorderComponent.builder()
                .applicationComponent(sensorApplication.getApplicationComponent())
                .build();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorsRecyclerView = (RecyclerView) view.findViewById(R.id.sensorsRecyclerView);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recorder;
    }

    @Override
    public RecorderPresenter providePresenter() {
        return recorderComponent.presenter();
    }

    @Override
    public String getTitle() {
        return getString(R.string.recorder_title);
    }
}
