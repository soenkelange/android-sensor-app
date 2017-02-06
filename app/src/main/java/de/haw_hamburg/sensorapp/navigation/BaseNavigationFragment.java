package de.haw_hamburg.sensorapp.navigation;

import de.haw_hamburg.sensorapp.BaseMvpFragment;
import de.haw_hamburg.sensorapp.mvp.Presenter;

/**
 * Created by s.lange on 05.02.17.
 */

public abstract class BaseNavigationFragment<P extends Presenter<V>, V extends de.haw_hamburg.sensorapp.mvp.View> extends BaseMvpFragment<P, V> {

    public abstract String getTitle();
}
