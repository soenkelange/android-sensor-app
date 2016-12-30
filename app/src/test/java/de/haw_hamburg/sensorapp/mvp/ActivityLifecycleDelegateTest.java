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
    private ActivityLifecycleDelegate lifecycleDelegate;

    @Before
    public void setUp() throws Exception {
        PresenterProvider presenterProvider = mock(PresenterProvider.class);
        ViewProvider viewProvider = mock(ViewProvider.class);
        lifecycleDelegate = new ActivityLifecycleDelegate(presenterProvider, viewProvider);
    }

    @Test
    public void onCreate_ShouldAttachView() {
        lifecycleDelegate.onCreate(new Bundle());

        verify(lifecycleDelegate.getPresenterViewBinder()).attachView();
    }

    @Test
    public void onDestroy_ShouldDetachView() {
        lifecycleDelegate.onDestroy();

        verify(lifecycleDelegate.getPresenterViewBinder()).detachView();
    }
}