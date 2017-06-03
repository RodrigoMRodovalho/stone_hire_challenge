package br.com.stone.store.presentation.base;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class MvpViewNotAttachedException extends RuntimeException {
    public MvpViewNotAttachedException() {
        super("Presenter.attachView(MvpView) must be call before"
                + " requesting data to the Presenter");
    }
}
