package com.njz.letsgoapp.wxapi;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.ui.UILifecycleListener;

/**
 * Created by LGQ
 * Time: 2018/10/31
 * Function:
 */

public class UpgradeDialogListener implements UILifecycleListener<UpgradeInfo> {
    @Override
    public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {
        LogUtil.e("bugly upload onCreate");
        TextView textView = view.findViewWithTag("beta_upgrade_info");
        textView.setTextColor(ContextCompat.getColor(AppUtils.getContext(), R.color.color_text));
    }

    @Override
    public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {
        LogUtil.e("bugly upload onStart");
    }

    @Override
    public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {
        LogUtil.e("bugly upload onResume");
        if (upgradeInfo.upgradeType == 2) {
            View lineView = view.findViewById(R.id.view_line);
            lineView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {
        LogUtil.e("bugly upload onPause");
    }

    @Override
    public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {
        LogUtil.e("bugly upload onStop");
    }

    @Override
    public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {
        LogUtil.e("bugly upload onDestroy");
    }
}
