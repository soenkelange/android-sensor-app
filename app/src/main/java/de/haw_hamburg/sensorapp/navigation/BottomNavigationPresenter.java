package de.haw_hamburg.sensorapp.navigation;

import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;

/**
 * Created by s.lange on 05.02.17.
 */
public class BottomNavigationPresenter extends AbstractPresenter<BottomNavigationView> {

    private static final int COMPASS_VIEW = 1;
    private static final int SPIRIT_LEVEL_VIEW = 2;
    private int currentView;


    public void initialize() {
        showCompass();
    }

    public void onCompassSelected() {
        if (!isCompassShown()) {
            showCompass();
        }
    }

    public boolean isCompassShown() {
        return currentView == COMPASS_VIEW;
    }

    private void showCompass() {
        getView().showCompass();
        currentView = COMPASS_VIEW;
    }

    public void onSpiritLevelSelected() {
        if (!isSpiritLevelShown()) {
            showSpiritLevel();
        }
    }

    public boolean isSpiritLevelShown() {
        return currentView == SPIRIT_LEVEL_VIEW;
    }

    private void showSpiritLevel() {
        getView().showSpiritLevel();
        currentView = SPIRIT_LEVEL_VIEW;
    }
}
