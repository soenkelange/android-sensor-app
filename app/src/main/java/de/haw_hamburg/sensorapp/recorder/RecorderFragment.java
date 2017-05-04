package de.haw_hamburg.sensorapp.recorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().initializeView();
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
    public void showStartButton() {
        showControlButton(R.string.recorder_control_start, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartClicked();
            }
        });
    }

    private void onStartClicked() {
        getPresenter().onStartClicked();
    }

    @Override
    public void hideStartButton() {
        hideControlButton();
    }

    @Override
    public void showStopButton() {
        showControlButton(R.string.recorder_control_stop, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopClicked();
            }
        });
    }

    private void onStopClicked() {
        getPresenter().onStopClicked();
    }

    @Override
    public void hideStopButton() {
        hideControlButton();
    }

    private void showControlButton(int stringRes, View.OnClickListener listener) {
        controlButton.setText(stringRes);
        controlButton.setVisibility(View.VISIBLE);
        controlButton.setOnClickListener(listener);
    }

    private void hideControlButton() {
        controlButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showStopRecordingDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.recorder_stopRecordingDialog_title))
                .setMessage(getString(R.string.recorder_stopRecordingDialog_message))
                .setPositiveButton(getString(R.string.recorder_stopRecordingDialog_stop), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onStopClicked();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getString(R.string.recorder_stopRecordingDialog_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void showRecorderSettings() {
        RecorderSettingsActivity.startActivity(getContext());
    }

    @Override
    public void exportCSV(Uri uri) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, uri);
        i.setType("text/*");
        startActivity(Intent.createChooser(i, "Email/Upload"));
    }
}
