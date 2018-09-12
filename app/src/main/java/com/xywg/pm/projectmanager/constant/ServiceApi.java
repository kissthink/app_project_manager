package com.xywg.pm.projectmanager.constant;


import com.xywg.pm.projectmanager.bean.BaseBean;
import com.xywg.pm.projectmanager.bean.BaseBeanFile;
import com.xywg.pm.projectmanager.bean.UserInfoBean;
import com.xywg.pm.projectmanager.bean.weather.WeatherBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by zhangyinlei on 2018/6/20
 */
public interface ServiceApi {

    // 登录功能
    @POST("user/login")
    Observable<BaseBean<UserInfoBean>> login(@Query("userName") String userName, @Query("password") String passWord, @Query("deviceId") String alipushDeviceId);

    // 获取天气预报
    @GET("http://wthrcdn.etouch.cn/weather_mini")
    Observable<WeatherBean> getWeatherByCityName(@Query("city") String cityName);

    // 上传图片一张图片
    @Multipart
    @POST("http://192.168.1.64:8081/app/project/fileuploadFtp?type=photo")
    Observable<BaseBeanFile<String>> uploadOneImage(@Part MultipartBody.Part part);

    // 上传多张图片
    @Multipart
    @POST("http://192.168.20.219:8081/app/project/fileuploadFtp?type=photo")
    Observable<String> uploadManyImages(@Part List<MultipartBody.Part> partList);

    // 上传录音文件
    @Multipart
    @POST("http://192.168.1.64:8081/app/project/fileuploadFtp?type=radio")
    Observable<BaseBeanFile<String>> uploadRadio(@Part MultipartBody.Part part);

}
