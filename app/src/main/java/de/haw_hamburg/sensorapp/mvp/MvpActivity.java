package de.haw_hamburg.sensorapp.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class MvpActivity<P extends Presenter<V>, V extends de.haw_hamburg.sensorapp.mvp.View> extends AppCompatActivity implements PresenterProvider<P>, ViewProvider<V> {

    private ActivityLifecycleDelegate lifecycleDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycleDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLifecycleDelegate().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLifecycleDelegate().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getLifecycleDelegate().onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getLifecycleDelegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getLifecycleDelegate().onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getLifecycleDelegate().onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycleDelegate().onDestroy();
    }

    public ActivityLifecycleDelegate<P, V> getLifecycleDelegate() {
        if (lifecycleDelegate == null) {
            PresenterViewBinder presenterViewBinder = new PresenterViewBinder(this, this);
            lifecycleDelegate = new ActivityLifecycleDelegate(presenterViewBinder);
        }
        return lifecycleDelegate;
    }

    @Override
    public V provideView() {
        return (V) this;
    }

    public P getPresenter() {
        return getLifecycleDelegate().getPresenter();
    }
}
