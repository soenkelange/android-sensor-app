package de.haw_hamburg.sensorapp.recorder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.SensorApplication;
import de.haw_hamburg.sensorapp.navigation.BaseNavigationFragment;
import de.haw_hamburg.sensorapp.recorder.settings.RecorderSettingsActivity;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Use the {@link RecorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecorderFragment extends BaseNavigationFragment<RecorderPresenter, RecorderView> implements RecorderView {

    private static final String TAG = RecorderFragment.class.getSimpleName();

    private RecorderComponent recorderComponent;

    private LinearLayout recorderControlsContainer;
    private LinearLayout noSensorsEnabledContainer;
    private ViewPager lineChartViewPager;
    private Button controlButton;
    private SensorLineChartPagerAdapter lineChartPagerAdapter;
    private Button openSettingsButton;

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
        recorderControlsContainer = (LinearLayout) view.findViewById(R.id.recorderControlsContainer);
        lineChartViewPager = (ViewPager) view.findViewById(R.id.lineChartsViewPager);
        lineChartPagerAdapter = new SensorLineChartPagerAdapter(getContext());
        lineChartViewPager.setAdapter(lineChartPagerAdapter);
        controlButton = (Button) view.findViewById(R.id.controlButton);
        controlButton.setOnClickListener(this::onControlButtonClicked);
        noSensorsEnabledContainer = (LinearLayout) view.findViewById(R.id.noSensorsEnabledContainer);
        openSettingsButton = (Button) view.findViewById(R.id.openSettingsButton);
        openSettingsButton.setOnClickListener(this::onOpenSettingsClicked);
        getPresenter().initialize();
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
                getPresenter().onSettingsMenuItemClicked();
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
    public void showRecorderControls() {
        noSensorsEnabledContainer.setVisibility(View.GONE);
        recorderControlsContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoSensorsEnabledHint() {
        recorderControlsContainer.setVisibility(View.GONE);
        noSensorsEnabledContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecorderSettings() {
        RecorderSettingsActivity.startActivity(getContext());
    }

    @Override
    public void addSensorLineChart(Sensor sensor) {
        lineChartPagerAdapter.addSensorLineChart(sensor);
    }
}
