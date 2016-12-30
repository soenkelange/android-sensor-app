package de.haw_hamburg.sensorapp.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class MvpFragment<P extends Presenter<V>, V extends de.haw_hamburg.sensorapp.mvp.View> extends Fragment implements PresenterProvider<P>, ViewProvider<V> {

    private FragmentLifecycleDelegate<P, V>  lifecycleDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycleDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycleDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLifecycleDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLifecycleDelegate().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLifecycleDelegate().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getLifecycleDelegate().onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getLifecycleDelegate().onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        getLifecycleDelegate().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getLifecycleDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycleDelegate().onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getLifecycleDelegate().onDetach();
    }

    private FragmentLifecycleDelegate<P, V> getLifecycleDelegate() {
        if (lifecycleDelegate == null) {
            PresenterViewBinder<P, V>  viewBinder = new PresenterViewBinder<>(this, this);
            lifecycleDelegate = new FragmentLifecycleDelegate<>(viewBinder);
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
