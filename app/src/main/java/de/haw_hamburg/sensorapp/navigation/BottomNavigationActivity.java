package de.haw_hamburg.sensorapp.navigation;

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
        showFragment(new CompassFragment());
    }

    @Override
    public void showSpiritLevel() {
        showFragment(new SpiritLevelFragment());
    }

    @Override
    public void showRecorder() {
        showFragment(new RecorderFragment());
    }

    private void showFragment(BaseNavigationFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
