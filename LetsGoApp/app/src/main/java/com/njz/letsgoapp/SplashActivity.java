package com.njz.letsgoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.njz.letsgoapp.util.PreferencesUtils;

/**
 * Created by LGQ
 * Time: 2018/8/21
 * Function:
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUserInfo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toHome();
            }
        }, 1500);
    }


    private void initUserInfo() {
        if (PreferencesUtils.getInstance().isLogin()) {
//            MySelfInfo.getInstance().getCache();
        }
    }

    private void toHome() {

        // 判断是否是第一次开启应用
        boolean isFirstOpened = PreferencesUtils.getInstance().getBoolean(PreferencesUtils.FIRST_OPENED,false);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpened) {
            PreferencesUtils.getInstance().putBoolean(PreferencesUtils.FIRST_OPENED,true);

            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return;
        }

//        startActivity(new Intent(this, HomeActivity.class));
        startActivity(new Intent(this, WelcomeActivity.class));
        this.finish();
    }
}
