package de.haw_hamburg.sensorapp.navigation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import de.haw_hamburg.sensorapp.BaseMvpActivity;
import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.compass.CompassFragment;
import de.haw_hamburg.sensorapp.recorder.RecorderFragment;
import de.haw_hamburg.sensorapp.spiritlevel.SpiritLevelFragment;

public class BottomNavigationActivity extends BaseMvpActivity<BottomNavigationPresenter, BottomNavigationView> implements BottomNavigationView {

    private static final String TAG = BottomNavigationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.design.widget.BottomNavigationView bottomNavigationView = (android.support.design.widget.BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        getPresenter().initialize();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_bottom_navigation;
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        if (!notRecording()) {
            return false;
        }
        switch (menuItem.getItemId()) {
            case R.id.menu_item_compass:
                getPresenter().onCompassSelected();
                return true;
            case R.id.menu_item_spiritLevel:
                getPresenter().onSpiritLevelSelected();
                return true;
            case R.id.menu_item_recorder:
                getPresenter().onRecorderSelected();
                return true;
        }
        return false;
    }

    @Override
    public BottomNavigationPresenter providePresenter() {
        if (getPresenter() == null) {
            return new BottomNavigationPresenter();
        }
        return getPresenter();
    }

    @Override
    public void showCompass() {
        showFragment(new CompassFragment(), "COMPASS");
    }

    @Override
    public void showSpiritLevel() {
        showFragment(new SpiritLevelFragment(), "SPIRIT");
    }

    @Override
    public void showRecorder() {
        showFragment(new RecorderFragment(), "RECORDER");
    }

    private boolean notRecording() {
        RecorderFragment fragment = (RecorderFragment)getSupportFragmentManager().findFragmentByTag("RECORDER");
        if (fragment != null) {
            if (fragment.getPresenter().isRecording() == true) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }

    private void resetRequestedOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    private void showFragment(BaseNavigationFragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }
}
