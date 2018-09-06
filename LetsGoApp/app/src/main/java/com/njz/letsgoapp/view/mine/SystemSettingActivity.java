package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.view.View;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.CacheUtil;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.widget.MineItemView;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class SystemSettingActivity extends BaseActivity implements View.OnClickListener {

    MineItemView system_setting_clean, system_setting_feedback, system_setting_about;

    Disposable disCleanCache;

    LoadingDialog loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_system_setting;
    }

    @Override
    public void initView() {

        showLeftAndTitle("设置");

        system_setting_clean = $(R.id.system_setting_clean);
        system_setting_feedback = $(R.id.system_setting_feedback);
        system_setting_about = $(R.id.system_setting_about);

        system_setting_feedback.setOnClickListener(this);
        system_setting_about.setOnClickListener(this);


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
                disCleanCache = RxBus2.getInstance().toObservable(String.class, new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        loadingDialog.dismiss();
                        system_setting_clean.setContent(s);
                        disCleanCache.dispose();
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CacheUtil.clearAllCache(AppUtils.getContext());
                        RxBus2.getInstance().post("0k");
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

        }
    }
}
