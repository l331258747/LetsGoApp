package com.njz.letsgoapp;

import android.app.Application;
import android.content.Context;

import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.log.ExceptionCrashHandler;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.SPUtils;
import com.njz.letsgoapp.util.AppUtils;
import com.taobao.sophix.SophixManager;
import com.tencent.bugly.Bugly;
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
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        context = getApplicationContext();

        if (displayWidth <= 0) {
            displayWidth = getResources().getDisplayMetrics().widthPixels;
        }

        if (displayHeight <= 0) {
            displayHeight = getResources().getDisplayMetrics().heightPixels;
        }

        //本地日志获取和bugly有冲突
//        ExceptionCrashHandler.getInstance().init(context);

        JPushInterface.init(context);
        JPushInterface.setDebugMode(true);

        SPUtils.init(context);
        AppUtils.init(this);
        LogUtil.setShowLog(true);

        registToWX();

        //第三个参数为SDK调试模式开关 建议在测试阶段建议设置成true，发布时设置为false。
//        CrashReport.initCrashReport(getApplicationContext(), "cd379e9015", true);
        Bugly.init(getApplicationContext(), "a6655ed7d5", true);

        //sophix 热修复
        SophixManager.getInstance().queryAndLoadNewPatch();

    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constant.WEIXIN_APP_ID);

    }
}
