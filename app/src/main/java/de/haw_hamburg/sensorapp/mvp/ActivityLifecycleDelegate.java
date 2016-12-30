package de.haw_hamburg.sensorapp.mvp;

import android.os.Bundle;

/**
 * Created by s.lange on 26.12.16.
 */
public class ActivityLifecycleDelegate {

    private final PresenterViewBinder viewBinder;

    public ActivityLifecycleDelegate(PresenterViewBinder viewBinder) {
        this.viewBinder = viewBinder;
    }

    public void onCreate(Bundle savedInstanceState) {
        viewBinder.attachView();
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onStop() {
    }

    public void onRestart() {
    }

    public void onDestroy() {
        viewBinder.detachView();
    }
}
