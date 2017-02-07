package de.haw_hamburg.sensorapp.navigation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by s.lange on 06.02.17.
 */
public class BottomNavigationPresenterTest {

    private BottomNavigationView view;
    private BottomNavigationPresenter bottomNavigationPresenter;

    @Before
    public void setUp() throws Exception {
        view = mock(BottomNavigationView.class);
        bottomNavigationPresenter = new BottomNavigationPresenter();
        bottomNavigationPresenter.attachView(view);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldShowCompassOnInitialization() {
        bottomNavigationPresenter.initialize();

        assertTrue(bottomNavigationPresenter.isCompassShown());
    }

    @Test
    public void shouldShowCompass() {
        bottomNavigationPresenter.onCompassSelected();

        verify(view).showCompass();
    }

    @Test
    public void whenCompassIsShown_ShouldNotShowCompassAgain() {
        bottomNavigationPresenter.onCompassSelected();

        bottomNavigationPresenter.onCompassSelected();

        verify(view, times(1)).showCompass();
    }

    @Test
    public void shouldShowSpiritLevel() {
        bottomNavigationPresenter.onSpiritLevelSelected();

        verify(view).showSpiritLevel();
    }

    @Test
    public void whenSpiritLevelIsShown_ShouldNotShowSpiritLevelAgain() {
        bottomNavigationPresenter.onSpiritLevelSelected();

        bottomNavigationPresenter.onSpiritLevelSelected();

        verify(view, times(1)).showSpiritLevel();
    }
}
