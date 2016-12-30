package de.haw_hamburg.sensorapp.mvp;

/**
 * Created by s.lange on 16.12.16.
 */

public interface Presenter<V extends View> {

    void attachView(V view);

    void detachView();
}
