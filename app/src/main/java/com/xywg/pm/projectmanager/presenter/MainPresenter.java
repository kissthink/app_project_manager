package com.xywg.pm.projectmanager.presenter;

import android.text.TextUtils;

import com.example.common.base.BasePresenter;
import com.example.common.network.retrofit.HttpUtil;
import com.example.common.sutils.utils.io.GsonUtil;
import com.xywg.pm.projectmanager.bean.BaseBeanFile;
import com.xywg.pm.projectmanager.bean.weather.WeatherBean;
import com.xywg.pm.projectmanager.bean.weather.WeatherJsonBean;
import com.xywg.pm.projectmanager.constant.Const;
import com.xywg.pm.projectmanager.constant.ServiceApi;
import com.xywg.pm.projectmanager.util.RetrofitUtil;

import io.reactivex.observers.DisposableObserver;

public class MainPresenter extends BasePresenter<IMainPresenter.IMainView> implements IMainPresenter {

    private String weatherJson = "";// 天气json值

    private double latitude;//纬度
    private double longitude;//经度

    public MainPresenter(IMainView mvpView) {
        super(mvpView);
    }

    @Override
    public void getWeatherByCityName(String cityName) {
        HttpUtil.getInstance().subscribeApi(
                HttpUtil.getInstance()
                        .createService(ServiceApi.class)
                        .getWeatherByCityName(cityName), new DisposableObserver<WeatherBean>() {
                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        WeatherBean.DataBean.ForecastBean forecastBean;
                        if (null == weatherBean
                                || null == weatherBean.getData()
                                || null == weatherBean.getData().getForecast()
                                || null == weatherBean.getData().getForecast().get(0))
                            return;
                        if (weatherBean.getStatus() != 1000)
                            return;
                        forecastBean = weatherBean.getData().getForecast().get(0);
                        String weather = "";
                        String tem = "";
                        String fl = "";
                        if (!TextUtils.isEmpty(forecastBean.getType())) {
                            weather = forecastBean.getType();
                        }
                        if (!TextUtils.isEmpty(forecastBean.getLow()) && !TextUtils.isEmpty(forecastBean.getHigh())) {
                            tem = forecastBean.getLow() + "~" + forecastBean.getHigh();
                        }
                        if (!TextUtils.isEmpty(forecastBean.getFengli())) {
                            fl = forecastBean.getFengxiang() + getFlString(forecastBean.getFengli());
                        }
                        weatherJson = GsonUtil.obj2String(new WeatherJsonBean(weather, tem, fl));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getFlString(String fl) {
        int length = fl.length();
        fl = fl.substring(length - 5, length - 3);
        return fl;
    }

    @Override
    public void uploadImage(String imagePath) {
        HttpUtil.getInstance().subscribeApi(
                HttpUtil.getInstance()
                        .createService(ServiceApi.class)
                        .uploadOneImage(RetrofitUtil.getUploadSingleImage("file", imagePath)), new DisposableObserver<BaseBeanFile<String>>() {
                    @Override
                    public void onNext(BaseBeanFile<String> stringBaseBeanFile) {
                        if (stringBaseBeanFile.getStatus() == Const.ConstantStatus.STATUS_SUCCESS) {
                            mvpView.onUploadImage(stringBaseBeanFile.getResultData());
                        } else {
                            mvpView.showError(stringBaseBeanFile.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mvpView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void uploadRadio(String radioPath, final int recordCount) {
        HttpUtil.getInstance().subscribeApi(
                HttpUtil.getInstance()
                        .createService(ServiceApi.class)
                        .uploadRadio(RetrofitUtil.getUploadSingleImage("file", radioPath)), new DisposableObserver<BaseBeanFile<String>>() {
                    @Override
                    public void onNext(BaseBeanFile<String> stringBaseBeanFile) {
                        if (stringBaseBeanFile.getStatus() == Const.ConstantStatus.STATUS_SUCCESS) {
                            mvpView.onUploadRadio(stringBaseBeanFile.getResultData(), recordCount);
                        } else {
                            mvpView.showError(stringBaseBeanFile.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mvpView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public String getWeatherJson() {
        return weatherJson;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatLon(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
