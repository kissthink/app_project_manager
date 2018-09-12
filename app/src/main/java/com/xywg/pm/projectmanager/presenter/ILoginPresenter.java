package com.xywg.pm.projectmanager.presenter;

import com.example.common.base.IBasePresenter;
import com.example.common.base.IBaseView;
import com.xywg.pm.projectmanager.bean.UserInfoBean;

public interface ILoginPresenter extends IBasePresenter {

    interface ILoginView extends IBaseView {

        void onLoginResult(UserInfoBean userInfoBean);
    }

    void login(String userName, String passWord, String alipushDeviceId);

}
