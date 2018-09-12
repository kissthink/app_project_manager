package com.example.common.base;

public class BasePresenter<V extends IBaseView> {

    protected V mvpView;

    public BasePresenter(V mvpView) {
        attachView(mvpView);
    }

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    public void detachView() {
        this.mvpView = null;
    }

}
