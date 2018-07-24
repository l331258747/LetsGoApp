package com.njz.letsgoapp;

import android.app.Application;
import android.content.Context;

import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.LogUtil;
import com.njz.letsgoapp.util.PreferencesUtils;
import com.njz.letsgoapp.util.Utils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function:
 */

public class MyApplication extends Application{

    private static MyApplication instance;

    private static Context context;

    public static IWXAPI mWxApi;

    /**
     * 屏幕尺寸
     */
    public static int displayWidth = 0;
    public static int displayHeight = 0;

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        if (displayWidth <= 0) {
            displayWidth = getResources().getDisplayMetrics().widthPixels;
        }

        if (displayHeight <= 0) {
            displayHeight = getResources().getDisplayMetrics().heightPixels;
        }

        JPushInterface.init(context);
        JPushInterface.setDebugMode(true);

        PreferencesUtils.init(context);
        Utils.init(this);
        LogUtil.setShowLog(true);

        registToWX();

    }

    public static Context getContext() {
        return context;
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constant.WEIXIN_APP_ID);

    }
}
