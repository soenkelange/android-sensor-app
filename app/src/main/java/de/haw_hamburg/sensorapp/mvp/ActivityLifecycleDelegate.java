package de.haw_hamburg.sensorapp.mvp;

import android.os.Bundle;

/**
 * Created by s.lange on 26.12.16.
 */
public class ActivityLifecycleDelegate<P extends Presenter<V>, V extends de.haw_hamburg.sensorapp.mvp.View> {

    private final PresenterViewBinder<P, V> viewBinder;

    public ActivityLifecycleDelegate(PresenterProvider<P> presenterProvider, ViewProvider<V> viewProvider) {
        this(new PresenterViewBinder<>(presenterProvider, viewProvider));
    }

    ActivityLifecycleDelegate(PresenterViewBinder viewBinder) {
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

    public P getPresenter() {
        return viewBinder.getPresenter();
    }
}
