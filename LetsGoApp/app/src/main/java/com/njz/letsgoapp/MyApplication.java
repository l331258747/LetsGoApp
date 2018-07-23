package com.njz.letsgoapp;

import android.app.Application;
import android.content.Context;

import com.njz.letsgoapp.util.LogUtil;
import com.njz.letsgoapp.util.PreferencesUtils;
import com.njz.letsgoapp.util.Utils;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function:
 */

public class MyApplication extends Application{

    private static MyApplication instance;

    private static Context context;

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

        PreferencesUtils.init(context);
        Utils.init(this);
        LogUtil.setShowLog(true);

    }

    public static Context getContext() {
        return context;
    }
}
