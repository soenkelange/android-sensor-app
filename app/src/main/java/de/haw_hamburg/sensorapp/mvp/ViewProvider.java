package de.haw_hamburg.sensorapp.mvp;

/**
 * Created by s.lange on 27.12.16.
 */

public interface ViewProvider<V extends View> {
    V provideView();
}
