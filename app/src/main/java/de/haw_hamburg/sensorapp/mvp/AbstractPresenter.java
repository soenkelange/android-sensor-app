package de.haw_hamburg.sensorapp.mvp;

/**
 * Created by s.lange on 16.12.16.
 */

public abstract class AbstractPresenter<V extends View> implements Presenter<V> {

    private View view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    View getView() {
        return view;
    }

    boolean isViewAttached() {
        return view != null;
    }
}
