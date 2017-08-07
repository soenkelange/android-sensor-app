package de.haw_hamburg.sensorapp.mvp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by s.lange on 16.12.16.
 */
public class AbstractPresenterTest {

    private View view;
    private AbstractPresenter abstractPresenter;

    @Before
    public void setUp() throws Exception {
        view = mock(View.class);
        abstractPresenter = Mockito.spy(AbstractPresenter.class);
    }

    @Test
    public void attachView_ShouldSetView() {
        abstractPresenter.attachView(view);

        assertSame(view, abstractPresenter.getView());
    }

    @Test
    public void detachView_ShouldSetViewToNull() {
        abstractPresenter.attachView(view);

        abstractPresenter.detachView();

        assertNull(abstractPresenter.getView());
    }

    @Test
    public void isViewAttached_ViewAttached_ShouldReturnTrue() {
        abstractPresenter.attachView(view);

        assertTrue(abstractPresenter.isViewAttached());
    }

    @Test
    public void isViewAttached_ViewNotAttached_ShouldReturnTrue() {
        assertFalse(abstractPresenter.isViewAttached());
    }
}