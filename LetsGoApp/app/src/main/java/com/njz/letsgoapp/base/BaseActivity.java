package com.njz.letsgoapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.StatusBarUtil;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by LGQ
 * Time: 2018/7/19
 * Function:
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Context context;
    public Activity activity;
    public Intent intent;

    ///////////////////////////////////////////////////
    private RelativeLayout titleLayout;
    private FrameLayout contentLayout;
    public ImageView leftIv;
    private TextView titleTv,rightTv;
    private ImageView rightIv;
    private View line;
    ///////////////////////////////////////////////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.setContentView(R.layout.activity_base); // 注意此处设置带title的布局

        context = this;
        activity = this;

        ActivityCollect.getAppCollect().addActivity(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        contentLayout = (FrameLayout) findViewById(R.id.base_content_layout);

        setContentView(getLayoutId());  /// 此处设置内容布局

        StatusBarUtil.setStatusBar(this, Color.WHITE);

        /////////////////
        getIntentData();
        initTitleView();
        initView();
        initData();
        /////////////////

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }

    /**
     * 方法重写
     *
     * @param resId
     */
    @Override
    public void setContentView(int resId) {
        View view = getLayoutInflater().inflate(resId, null);
        if (null != contentLayout)
            contentLayout.addView(view);
    }

    //----------------titlebar start--------------
    private void initTitleView() {
        leftIv = $(R.id.left_iv);
        titleTv = $(R.id.title_tv);
        rightTv = $(R.id.right_tv);
        rightIv = $(R.id.right_iv);
        line = $(R.id.line);

        leftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private long mExitTime;
    @Override
    public void onBackPressed() {
        if(activity instanceof HomeActivity){
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showLongToast("再按一次退出那就走旅行");
                mExitTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();
    }

    //----------------------titlebar end-----------------------

    //-------------------title设置 start---------------

    /**
     * 隐藏 TitleLayout，全屏效果，登录，和自定义标题样式使用
     */
    public void hideTitleLayout() {
        titleLayout.setVisibility(View.GONE);
    }

    public void showTitleLayout() {
        titleLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 显示标题信息，和返回键
     */
    public void showLeftAndTitle(String title) {
        showLeftIcon();
        getTitleTv().setText(title);
    }


    /**
     * 隐藏返回键
     */
    public void hideLeftIv() {
        leftIv.setVisibility(View.GONE);
    }

    //获得标题控件
    public TextView getTitleTv() {
        return titleTv;
    }

    //显示返回键
    public void showLeftIcon() {
        showView(leftIv);
    }

    public void showTitleTv() {
        showView(titleTv);
    }

    protected void showView(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public TextView getRightTv() {
        return rightTv;
    }

    //显示右边 tv
    public void showRightTv(){
        showView(rightTv);
    }

    public ImageView getRightIv(){
        return rightIv;
    }

    //显示右边iv
    public void showRightIv(){
        showView(rightIv);
    }


    //显示line
    public void hideLine(){
        line.setVisibility(View.INVISIBLE);
    }

    //-------------------title设置 end---------------


    /**
     * 获取传递的参数
     **/
    public void getIntentData() {
        intent = getIntent();
    }

    /**
     * 获取布局 Layout
     **/
    public abstract int getLayoutId();

    /**
     * 初始化 View
     **/
    public abstract void initView();

    /**
     * 初始化数据
     **/
    public abstract void initData();

    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    public void showShortToast(String msg) {
        ToastUtil.showShortToast(context, msg);
    }

    public void showLongToast(String msg) {
        ToastUtil.showLongToast(this, msg);
    }

    public int getResColor(int resId) {
        return ContextCompat.getColor(this,resId);

    }

    public String getResString(int resId) {
        return getResources().getString(resId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollect.getAppCollect().finishActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return false;
    }

    //-------------start 适配 字体
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
    //-------------end 适配 字体

}
