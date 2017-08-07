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

    private PresenterViewBinder viewBinder;
    private FragmentLifecycleDelegate lifecycleDelegate;

    @Before
    public void setUp() throws Exception {
        viewBinder = mock(PresenterViewBinder.class);
        lifecycleDelegate = new FragmentLifecycleDelegate(viewBinder);
    }

    @Test
    public void onViewCreated_ShouldAttachView() {
        lifecycleDelegate.onViewCreated(mock(android.view.View.class), new Bundle());

        verify(viewBinder).attachView();
    }


    @Test
    public void onDestroyView_ShouldDetachView() {
        lifecycleDelegate.onDestroyView();

        verify(viewBinder).detachView();
    }
}