package de.haw_hamburg.sensorapp.navigation;

import de.haw_hamburg.sensorapp.mvp.View;

/**
 * Created by s.lange on 05.02.17.
 */
public interface BottomNavigationView extends View {
    void showCompass();

    void showSpiritLevel();

    void showRecorder();
}
