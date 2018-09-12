package com.xywg.pm.projectmanager.presenter;

import com.example.common.base.BasePresenter;
import com.example.common.network.retrofit.HttpUtil;
import com.xywg.pm.projectmanager.bean.BaseBean;
import com.xywg.pm.projectmanager.bean.UserInfoBean;
import com.xywg.pm.projectmanager.constant.Const;
import com.xywg.pm.projectmanager.constant.ServiceApi;

import io.reactivex.observers.DisposableObserver;

public class LoginPresenter extends BasePresenter<ILoginPresenter.ILoginView> implements ILoginPresenter {

    public LoginPresenter(ILoginView mvpView) {
        super(mvpView);
    }

    @Override
    public void login(final String userName, String passWord, String alipushDeviceId) {
        mvpView.showProgress();
        HttpUtil.getInstance().subscribeApi(
                HttpUtil.getInstance().createService(ServiceApi.class)
                        .login(userName, passWord, alipushDeviceId), new DisposableObserver<BaseBean<UserInfoBean>>() {
                    @Override
                    public void onNext(BaseBean<UserInfoBean> userInfoBean) {
                        if (userInfoBean.getStatus() == Const.ConstantStatus.STATUS_SUCCESS) {
                            mvpView.onLoginResult(userInfoBean.getData());
                        } else {
                            mvpView.showError(userInfoBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mvpView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mvpView.hideProgress();
                    }
                });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

}
