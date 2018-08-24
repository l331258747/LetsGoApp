package com.njz.letsgoapp.util.http;

import com.njz.letsgoapp.bean.BaseResponse;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.order.AliPay;
import com.njz.letsgoapp.bean.MovieSubject;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by LGQ
 * Time: 2018/7/18
 * Function:
 */

public class MethodApi {

    /**
     * 获取用户详细信息
     *
     * @Field("telphone") String telphone,
     * @Field("passWord") String passWord,
     * @Field("captcha") String captcha,
     * @Field("channel") int channel
     */
//    public static void register(DisposableObserver<BaseResponse> subscriber
//            , String telphone, String passWord, String captcha, int channel) {
//        Observable observable = HttpMethods.getInstance().getHttpService().register(telphone,
//                passWord,
//                captcha,
//                channel); //在HttpServer中
//        HttpMethods.getInstance().toSubscribe(observable, subscriber);
//    }
    public static void getTop250(DisposableObserver<MovieSubject> subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().getTop250(0, 2); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void appPay(DisposableObserver<AliPay> subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().appPay(); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void appPayWX(DisposableObserver<AliPay> subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().appPayWX(); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }


    //-----------login start------------
    public static void login(String mobile, String password, DisposableObserver subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().login(mobile, password); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void msgCheckRegister(String mobile, String msg, String password, DisposableObserver subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().msgCheckRegister(mobile, msg, password); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }


    public static void msgCheckFindBy(String mobile, String msg, String password, DisposableObserver subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().msgCheckFindBy(mobile, msg, password); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void msgCheckLogin(String mobile, String msg, DisposableObserver subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().msgCheckLogin(mobile, msg); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void userSmsSend(String mobile, String type, DisposableObserver subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().userSmsSend(mobile, type); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void changePwd(String token, String password, String newPassword, DisposableObserver subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().changePwd(token, password, newPassword); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void updateMobile(String token, String mobile, String msg, String password, DisposableObserver subscriber) {
        Observable observable = HttpMethods.getInstance().getHttpService().updateMobile(token, mobile, msg, password); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    //-----------login end------------

}
