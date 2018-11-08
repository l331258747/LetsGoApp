package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.mvp.login.LogoutContract;
import com.njz.letsgoapp.mvp.login.LogoutPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.CacheUtil;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CleanCacheEvent;
import com.njz.letsgoapp.widget.MineItemView;
import com.tencent.bugly.beta.Beta;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class SystemSettingActivity extends BaseActivity implements View.OnClickListener,LogoutContract.View {

    MineItemView system_setting_clean, system_setting_feedback, system_setting_about,system_setting_upload;

    Disposable disCleanCache;

    LoadingDialog loadingDialog;

    Button btnLoginoff;

    LogoutPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_system_setting;
    }

    @Override
    public void initView() {

        showLeftAndTitle("系统设置");

        system_setting_clean = $(R.id.system_setting_clean);
        system_setting_feedback = $(R.id.system_setting_feedback);
        system_setting_about = $(R.id.system_setting_about);
        system_setting_upload = $(R.id.system_setting_upload);

        system_setting_feedback.setOnClickListener(this);
        system_setting_about.setOnClickListener(this);
        system_setting_upload.setOnClickListener(this);

        btnLoginoff = $(R.id.btn_loginoff);
        btnLoginoff.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MySelfInfo.getInstance().isLogin()){
            btnLoginoff.setVisibility(View.VISIBLE);
        }else{
            btnLoginoff.setVisibility(View.GONE);
        }
    }

    public void initCLean() {
        try {
            String cacheSize = CacheUtil.getTotalCacheSize(AppUtils.getContext());
            system_setting_clean.setContent(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        system_setting_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.showDialog("清理中...");
                loadingDialog.setCancelable(false);
                disCleanCache = RxBus2.getInstance().toObservable(CleanCacheEvent.class, new Consumer<CleanCacheEvent>() {
                    @Override
                    public void accept(CleanCacheEvent s) throws Exception {
                        loadingDialog.dismiss();
                        system_setting_clean.setContent("0k");
                        disCleanCache.dispose();
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CacheUtil.clearAllCache(AppUtils.getContext());
                        RxBus2.getInstance().post(new CleanCacheEvent());
                    }
                }).start();
            }
        });
    }

    @Override
    public void initData() {
        loadingDialog = new LoadingDialog(context);
        initCLean();

        system_setting_about.setContent("当前版本 " + AppUtils.getVersionName());

        mPresenter = new LogoutPresenter(context,this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_setting_feedback:
                startActivity(new Intent(context, FeedbackActivity.class));
                break;
            case R.id.system_setting_about:
                startActivity(new Intent(context, AboutActivity.class));
                break;
            case R.id.btn_loginoff:
                mPresenter.userLogout();
                break;
            case R.id.system_setting_upload:
                Beta.checkUpgrade();
                break;
        }
    }

    @Override
    public void userLogoutSuccess(EmptyModel str) {
        showShortToast("退出");
        MySelfInfo.getInstance().loginOff();
        finish();
    }

    @Override
    public void userLogoutFailed(String msg) {
        showShortToast(msg);
        MySelfInfo.getInstance().loginOff();
        finish();
    }
}
