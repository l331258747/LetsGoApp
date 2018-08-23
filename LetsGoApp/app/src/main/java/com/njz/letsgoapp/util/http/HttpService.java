package com.njz.letsgoapp.util.http;

import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.order.AliPay;
import com.njz.letsgoapp.bean.MovieSubject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function: 请求
 */

public interface HttpService {

    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count")int count);

    @GET("alipay/appPay")
    Observable<AliPay> appPay();


    @GET("wxpay/appPay")
    Observable<AliPay> appPayWX();

    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginModel> login(
            @Field("mobile") String mobile,
            @Field("password") String password
    );


}
