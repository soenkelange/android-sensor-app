package de.haw_hamburg.sensorapp.mvp;

/**
 * Created by s.lange on 27.12.16.
 */

class PresenterViewBinder<P extends Presenter<V>, V extends View> {
    private final PresenterProvider<P> presenterProvider;
    private final ViewProvider<V> viewProvider;
    private P presenter;

    PresenterViewBinder(PresenterProvider<P> presenterProvider, ViewProvider<V> viewProvider) {
        this.presenterProvider = presenterProvider;
        this.viewProvider = viewProvider;
    }

    void attachView() {
        presenter = presenterProvider.providePresenter();
        presenter.attachView(viewProvider.provideView());
    }

    void detachView() {
        presenter.detachView();
    }

    P getPresenter() {
        return presenter;
    }
}
