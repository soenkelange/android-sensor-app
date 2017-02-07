package de.haw_hamburg.sensorapp.spiritlevel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.navigation.BaseNavigationFragment;


public class SpiritLevelFragment extends BaseNavigationFragment<SpiritLevelPresenter, SpiritLevelView> implements SpiritLevelView {

    public SpiritLevelFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_spirit_level;
    }

    @Override
    public String getTitle() {
        return getString(R.string.spiritLevel_title);
    }

    @Override
    public SpiritLevelPresenter providePresenter() {
        return new SpiritLevelPresenter();
    }
}
