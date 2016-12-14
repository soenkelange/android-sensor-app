package de.haw_hamburg.sensorapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ExporterFragment extends Fragment {

    public ExporterFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d(ExporterFragment.class.getSimpleName(), "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_exporter, container, false);

        return rootView;
    }
}
