package br.com.stone.store.presentation.base;

/**
 * Created by rrodovalho on 03/06/17.
 */

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
