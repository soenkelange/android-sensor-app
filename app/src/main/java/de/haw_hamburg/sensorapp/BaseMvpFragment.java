package de.haw_hamburg.sensorapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.haw_hamburg.sensorapp.mvp.MvpFragment;
import de.haw_hamburg.sensorapp.mvp.Presenter;

public abstract class BaseMvpFragment<P extends Presenter<V>, V extends de.haw_hamburg.sensorapp.mvp.View> extends MvpFragment<P, V> {

    public BaseMvpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutResource(), container, false);
    }

    protected abstract int getLayoutResource();
}
