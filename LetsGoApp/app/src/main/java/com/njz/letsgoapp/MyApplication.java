package com.njz.letsgoapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.log.ExceptionCrashHandler;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.PreferencesUtils;
import com.njz.letsgoapp.util.AppUtils;
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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //Dalvik在5.0之前，为每一个APK只生成一个classes.dex，
        // 所以会有上述所说的方法数超限的问题，如果我们可以将一个dex文件分成多个，
        // 在应用启动时，加载第一个（主dex）dex文件，当启动以后，再依次加载其他dex文件。这样就可以规避上述问题了。
        // MultiDex即是实现了这样的功能
        MultiDex.install(this);
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

        JPushInterface.init(context);
        JPushInterface.setDebugMode(true);

        PreferencesUtils.init(context);
        AppUtils.init(this);
        LogUtil.setShowLog(true);

        registToWX();

        //第三个参数为SDK调试模式开关 建议在测试阶段建议设置成true，发布时设置为false。
//        CrashReport.initCrashReport(getApplicationContext(), "cd379e9015", true);
        Bugly.init(getApplicationContext(), "cd379e9015", true);

    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constant.WEIXIN_APP_ID);

    }
}
