package de.haw_hamburg.sensorapp.mvp;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by s.lange on 29.12.16.
 */
public class ActivityLifecycleDelegateTest {
    private PresenterViewBinder presenterViewBinder;
    private ActivityLifecycleDelegate lifecycleDelegate;

    @Before
    public void setUp() throws Exception {
        presenterViewBinder = mock(PresenterViewBinder.class);
        lifecycleDelegate = new ActivityLifecycleDelegate(presenterViewBinder);
    }

    @Test
    public void onCreate_ShouldAttachView() {
        lifecycleDelegate.onCreate(new Bundle());

        verify(presenterViewBinder).attachView();
    }

    @Test
    public void onDestroy_ShouldDetachView() {
        lifecycleDelegate.onDestroy();

        verify(presenterViewBinder).detachView();
    }
}