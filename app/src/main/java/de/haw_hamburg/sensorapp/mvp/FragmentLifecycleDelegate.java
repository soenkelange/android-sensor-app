package de.haw_hamburg.sensorapp.mvp;

import android.os.Bundle;
import android.view.View;

/**
 * Created by s.lange on 26.12.16.
 */
public class FragmentLifecycleDelegate {

    private final PresenterViewBinder viewBinder;

    public FragmentLifecycleDelegate(PresenterViewBinder viewBinder) {
        this.viewBinder = viewBinder;
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        viewBinder.attachView();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
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

    public void onDestroyView() {
        viewBinder.detachView();
    }

    public void onDestroy() {
    }

    public void onDetach() {
    }
}
