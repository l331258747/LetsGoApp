package com.njz.letsgoapp.util.http;

import com.njz.letsgoapp.bean.BaseResponse;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.GuideDetailModel;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.login.VerifyModel;
import com.njz.letsgoapp.bean.order.AliPay;
import com.njz.letsgoapp.bean.MovieSubject;

import java.util.List;

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
    @POST("travel/user/login")
    Observable<BaseResponse<LoginModel>> login(
            @Field("mobile") String mobile,
            @Field("password") String password
    );

    //注册
    @FormUrlEncoded
    @POST("travel/sms/msgCheckRegister")
    Observable<BaseResponse<EmptyModel>> msgCheckRegister(
            @Field("mobile") String mobile,
            @Field("msg") String msg,
            @Field("password") String password
    );

    //找回密码
    @FormUrlEncoded
    @POST("travel/sms/msgCheckFindBy")
    Observable<BaseResponse<EmptyModel>> msgCheckFindBy(
            @Field("mobile") String mobile,
            @Field("msg") String msg,
            @Field("newPassword") String newPassword
    );

    //手机验证码登录
    @FormUrlEncoded
    @POST("travel/sms/msgCheckLogin")
    Observable<BaseResponse<LoginModel>> msgCheckLogin(
            @Field("mobile") String mobile,
            @Field("msg") String msg
    );

    //短信验证码
    @FormUrlEncoded
    @POST("travel/sms/userSmsSend")
    Observable<BaseResponse<VerifyModel>> userSmsSend(
            @Field("mobile") String mobile,
            @Field("type") String type
    );


    //修改密码
    @FormUrlEncoded
    @POST("travel/user/changePwd")
    Observable<BaseResponse<EmptyModel>> changePwd(
//            @Field("X-Nideshop-Token") String token,
            @Field("password") String password,
            @Field("newPassword") String newPassword
    );

    //修改手机号
    @FormUrlEncoded
    @POST("travel/sms/updateMobile")
    Observable<BaseResponse<EmptyModel>> updateMobile(
//            @Field("X-Nideshop-Token") String token,
            @Field("mobile") String mobile,
            @Field("msg") String msg,
            @Field("password") String password
    );

    //登录系列   end


    //首页 start
    //轮播图 banner/findAll
    @GET("banner/findByTypeAndGuideId")
    Observable<BaseResponse<List<BannerModel>>> bannerFindByType(
            @Query("type") int type,
            @Query("guideId") int guideId
    );

    //首页获取热门导游
    @GET("orderReviews/sortTop")
        Observable<BaseResponse<List<GuideModel>>> orderReviewsSortTop(
            @Query("location") String location
    );

    //首页获取热动态
    @GET("region/getSterByLocation")
    Observable<BaseResponse<DynamicListModel>> regionGetSterByLocation(
            @Query("location") String location,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //获取导游列表
    @GET("guide/sortTop10ByLocation")
    Observable<BaseResponse<GuideListModel>> guideSortTop10ByLocation(
            @Query("location") String location,
            @Query("type") int type,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //获取导游详情
    @GET("guide/findGuideDetails")
    Observable<BaseResponse<GuideDetailModel>> guideFindGuideDetails(
            @Query("location") String location,
            @Query("guideId") int guideId
    );

    //首页 end


    //城市选择 region/findProAndCity
    @GET("region/findProAndCity")
    Observable<BaseResponse<EmptyModel>> regionFindProAndCity(
    );

}
