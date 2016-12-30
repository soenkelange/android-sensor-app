package de.haw_hamburg.sensorapp.mvp;

/**
 * Created by s.lange on 27.12.16.
 */

public class PresenterViewBinder<P extends Presenter<V>, V extends View> {
    private final PresenterProvider<P> presenterProvider;
    private final ViewProvider<V> viewProvider;
    private P presenter;

    public PresenterViewBinder(PresenterProvider<P> presenterProvider, ViewProvider<V> viewProvider) {
        this.presenterProvider = presenterProvider;
        this.viewProvider = viewProvider;
    }

    public void attachView() {
        presenter = presenterProvider.providePresenter();
        presenter.attachView(viewProvider.provideView());
    }

    public void detachView() {
        presenter.detachView();
    }
}
