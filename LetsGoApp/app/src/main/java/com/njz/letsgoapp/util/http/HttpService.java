package com.njz.letsgoapp.util.http;

import com.njz.letsgoapp.bean.BaseResponse;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.login.VerifyModel;
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

    //登录系列   start
    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponse<LoginModel>> login(
            @Field("mobile") String mobile,
            @Field("password") String password
    );

    //注册
    @FormUrlEncoded
    @POST("sms/msgCheckRegister")
    Observable<BaseResponse<EmptyModel>> msgCheckRegister(
            @Field("mobile") String mobile,
            @Field("msg") String msg,
            @Field("password") String password
    );

    //找回密码
    @FormUrlEncoded
    @POST("sms/msgCheckFindBy")
    Observable<BaseResponse<EmptyModel>> msgCheckFindBy(
            @Field("mobile") String mobile,
            @Field("msg") String msg,
            @Field("newPassword") String newPassword
    );

    //手机验证码登录
    @FormUrlEncoded
    @POST("sms/msgCheckLogin")
    Observable<BaseResponse<LoginModel>> msgCheckLogin(
            @Field("mobile") String mobile,
            @Field("msg") String msg
    );

    //短信验证码
    @FormUrlEncoded
    @POST("sms/userSmsSend")
    Observable<BaseResponse<VerifyModel>> userSmsSend(
            @Field("mobile") String mobile,
            @Field("type") String type
    );


    //修改密码
    @FormUrlEncoded
    @POST("user/changePwd")
    Observable<BaseResponse<EmptyModel>> changePwd(
            @Field("X-Nideshop-Token") String token,
            @Field("password") String password,
            @Field("newPassword") String newPassword
    );
    //登录系列   end


}
