package de.haw_hamburg.sensorapp.recorder;

import javax.inject.Inject;

import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;

/**
 * Created by s.lange on 29.12.16.
 */

public class RecorderPresenter extends AbstractPresenter<RecorderView> {

    @Inject
    public RecorderPresenter() {
    }

    public void onSettingsClicked() {
        getView().showRecorderSettings();
    }
}
