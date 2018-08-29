package com.njz.letsgoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.njz.letsgoapp.util.SPUtils;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;

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
        if (SPUtils.getInstance().isLogin()) {
//            MySelfInfo.getInstance().getCache();
        }
    }

    private void toHome() {

        // 判断是否是第一次开启应用
        boolean isFirstOpened = SPUtils.getInstance().getBoolean(SPUtils.FIRST_OPENED, false);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpened) {
            SPUtils.getInstance().putBoolean(SPUtils.FIRST_OPENED, true);

            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return;
        }

        Intent intent = new Intent(this, HomeActivity.class);

//        Intent intent = new Intent(this, GuideDetailActivity.class);
//        intent.putExtra(GuideDetailActivity.GUIDEID, 4);

        startActivity(intent);

        this.finish();
    }
}
