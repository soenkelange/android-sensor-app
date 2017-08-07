package de.haw_hamburg.sensorapp.mvp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by s.lange on 27.12.16.
 */
public class PresenterViewBinderTest {

    private Presenter presenter;
    private View view;
    private PresenterProvider presenterProvider;
    private ViewProvider viewProvider;
    private PresenterViewBinder presenterViewBinder;

    @Before
    public void setUp() throws Exception {
        presenter = mock(Presenter.class);
        view = mock(View.class);
        presenterProvider = mock(PresenterProvider.class);
        viewProvider = mock(ViewProvider.class);
        when(presenterProvider.providePresenter()).thenReturn(presenter);
        when(viewProvider.provideView()).thenReturn(view);
        presenterViewBinder = new PresenterViewBinder(presenterProvider, viewProvider);
    }

    @Test
    public void attach_ShouldAttachViewToPresenter() {
        presenterViewBinder.attachView();

        verify(presenter).attachView(view);
    }

    @Test
    public void detach_ShouldCallDetachViewOnPresenter() {
        presenterViewBinder.attachView();

        presenterViewBinder.detachView();

        verify(presenter).detachView();
        verify(presenterProvider, times(1)).providePresenter();
    }
}