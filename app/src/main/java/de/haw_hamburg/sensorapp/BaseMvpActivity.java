package de.haw_hamburg.sensorapp;

import android.os.Bundle;

import de.haw_hamburg.sensorapp.mvp.MvpActivity;
import de.haw_hamburg.sensorapp.mvp.Presenter;

/**
 * Created by s.lange on 05.02.17.
 */

public abstract class BaseMvpActivity<P extends Presenter<V>, V extends de.haw_hamburg.sensorapp.mvp.View> extends MvpActivity<P, V> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
    }

    protected abstract int getLayoutResource();
}
