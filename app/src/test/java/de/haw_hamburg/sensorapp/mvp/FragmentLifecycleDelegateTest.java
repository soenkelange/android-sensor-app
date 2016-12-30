package de.haw_hamburg.sensorapp.mvp;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by s.lange on 29.12.16.
 */
public class FragmentLifecycleDelegateTest {

    private FragmentLifecycleDelegate lifecycleDelegate;

    @Before
    public void setUp() throws Exception {
        PresenterProvider presenterProvider = mock(PresenterProvider.class);
        ViewProvider viewProvider = mock(ViewProvider.class);
        lifecycleDelegate = new FragmentLifecycleDelegate(presenterProvider, viewProvider);
    }

    @Test
    public void onViewCreated_ShouldAttachView() {
        lifecycleDelegate.onViewCreated(mock(android.view.View.class), new Bundle());

        verify(lifecycleDelegate.getPresenterViewBinder()).attachView();
    }


    @Test
    public void onDestroyView_ShouldDetachView() {
        lifecycleDelegate.onDestroyView();

        verify(lifecycleDelegate.getPresenterViewBinder()).detachView();
    }
}