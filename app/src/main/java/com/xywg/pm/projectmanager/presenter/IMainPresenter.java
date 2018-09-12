package com.xywg.pm.projectmanager.presenter;

import com.example.common.base.IBasePresenter;
import com.example.common.base.IBaseView;

public interface IMainPresenter extends IBasePresenter {

    interface IMainView extends IBaseView {

        void onUploadImage(String imageUrl);

        void onUploadRadio(String radioUrl, int recordCount);
    }

    void getWeatherByCityName(String cityName);

    void uploadImage(String imagePath);

    void uploadRadio(String radioPath, int recordCount);

}
