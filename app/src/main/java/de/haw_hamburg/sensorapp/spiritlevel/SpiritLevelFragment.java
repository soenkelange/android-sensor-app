package de.haw_hamburg.sensorapp.spiritlevel;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.navigation.BaseNavigationFragment;

public class SpiritLevelFragment extends BaseNavigationFragment<SpiritLevelPresenter, SpiritLevelPresenterView> implements SpiritLevelPresenterView, SpiritLevel.OnRotationChangedListener {

    private SpiritLevelView spiritLevelView;
    private SpiritLevel spiritLevel;

    public SpiritLevelFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        spiritLevelView = (SpiritLevelView) rootView.findViewById(R.id.spiritLevelView);
        if (spiritLevel == null) {
            spiritLevel = new SpiritLevel(getContext(), this);
        }
        return rootView;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_spirit_level;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            spiritLevel.start();
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.spiritLevel_title);
    }

    @Override
    public void onPause() {
        super.onPause();
        spiritLevel.stop();

    }

    @Override
    public void onRotationChanged(float pitch, float roll) {
        spiritLevelView.changeSpiritLevel(pitch, roll);
    }

    @Override
    public SpiritLevelPresenter providePresenter() {
        return new SpiritLevelPresenter();
    }
}
