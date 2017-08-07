package de.haw_hamburg.sensorapp.navigation;

import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;

/**
 * Created by s.lange on 05.02.17.
 */
public class BottomNavigationPresenter extends AbstractPresenter<BottomNavigationView> {

    private static final int COMPASS_VIEW = 1;
    private static final int SPIRIT_LEVEL_VIEW = 2;
    private static final int RECORDER_VIEW = 3;
    private int currentView;


    public void initialize() {
        showCompass();
    }

    public void onCompassSelected() {
        if (!isCurrentView(COMPASS_VIEW)) {
            showCompass();
        }
    }

    public boolean isCompassShown() {
        return isCurrentView(COMPASS_VIEW);
    }

    private void showCompass() {
        getView().showCompass();
        currentView = COMPASS_VIEW;
    }

    public void onSpiritLevelSelected() {
        if (!isCurrentView(SPIRIT_LEVEL_VIEW)) {
            showSpiritLevel();
        }
    }

    private void showSpiritLevel() {
        getView().showSpiritLevel();
        currentView = SPIRIT_LEVEL_VIEW;
    }

    public void onRecorderSelected() {
        if (!isCurrentView(RECORDER_VIEW)) {
            showRecorder();
        }
    }

    private void showRecorder() {
        getView().showRecorder();
        currentView = RECORDER_VIEW;
    }

    private boolean isCurrentView(int view) {
        return currentView == view;
    }
}
