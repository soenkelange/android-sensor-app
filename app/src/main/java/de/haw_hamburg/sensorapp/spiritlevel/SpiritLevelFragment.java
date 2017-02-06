package de.haw_hamburg.sensorapp.spiritlevel;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.haw_hamburg.sensorapp.R;


public class SpiritLevelFragment extends Fragment implements SpiritLevel.OnRotationChangedListener {

    private SpiritLevelView spiritLevelView;
    private SpiritLevel spiritLevel;

    public SpiritLevelFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_spirit_level, container, false);
        spiritLevelView = (SpiritLevelView) rootView.findViewById(R.id.spiritLevelView);
        if (spiritLevel == null) {
            spiritLevel = new SpiritLevel(getContext(), this);
        }
        return rootView;
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
    public void onPause() {
        super.onPause();
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            spiritLevel.stop();
        }
    }

    @Override
    public void onRotationChanged(float pitch, float roll) {
        spiritLevelView.changeSpiritLevel(pitch, roll);
    }
}
