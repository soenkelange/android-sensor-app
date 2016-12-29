package de.haw_hamburg.sensorapp.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MvpActivity extends AppCompatActivity implements PresenterProvider, ViewProvider {

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

    public ActivityLifecycleDelegate getLifecycleDelegate() {
        if (lifecycleDelegate == null) {
            PresenterViewBinder presenterViewBinder = new PresenterViewBinder(this, this);
            lifecycleDelegate = new ActivityLifecycleDelegate(presenterViewBinder);
        }
        return lifecycleDelegate;
    }

    @Override
    public Presenter providePresenter() {
        return null;
    }

    @Override
    public View provideView() {
        return null;
    }
}
