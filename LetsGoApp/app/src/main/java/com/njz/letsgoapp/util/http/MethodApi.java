package com.njz.letsgoapp.util.http;

import com.njz.letsgoapp.bean.AliPay;
import com.njz.letsgoapp.bean.MovieSubject;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LGQ
 * Time: 2018/7/18
 * Function:
 */

public class MethodApi {

    /**
     * 获取用户详细信息
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

    public static void getTop250(DisposableObserver<MovieSubject> subscriber){
        Observable observable = HttpMethods.getInstance().getHttpService().getTop250(0,2); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

    public static void appPay(DisposableObserver<AliPay> subscriber){
        Observable observable = HttpMethods.getInstance().getHttpService().appPay(); //在HttpServer中
        HttpMethods.getInstance().toSubscribe(observable, subscriber);
    }

}
