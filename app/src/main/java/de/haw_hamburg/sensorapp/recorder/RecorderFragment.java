package de.haw_hamburg.sensorapp.recorder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;

import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.SensorApplication;
import de.haw_hamburg.sensorapp.navigation.BaseNavigationFragment;
import de.haw_hamburg.sensorapp.recorder.settings.RecorderSettingsActivity;

/**
 * Use the {@link RecorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecorderFragment extends BaseNavigationFragment<RecorderPresenter, RecorderView> implements RecorderView {

    private static final String TAG = RecorderFragment.class.getSimpleName();
    private Button controlButton;
    private LineChart lineChart;
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
        setHasOptionsMenu(true);
        SensorApplication sensorApplication = (SensorApplication) getActivity().getApplication();
        recorderComponent = DaggerRecorderComponent.builder()
                .applicationComponent(sensorApplication.getApplicationComponent())
                .build();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controlButton = (Button) view.findViewById(R.id.controlButton);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_recorder, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_recorder_settings:
                getPresenter().onSettingsClicked();
                return true;

        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void showRecorderSettings() {
        RecorderSettingsActivity.startActivity(getContext());
    }
}
