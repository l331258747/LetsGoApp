package com.njz.letsgoapp;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.SPUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by LGQ
 * Time: 2018/8/21
 * Function:
 */

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideBottomUIMenu();

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        initUserInfo();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        hideTitleLayout();
    }

    @Override
    public void initData() {

    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    private void initUserInfo() {
    }

    private void goHome(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toHome();
            }
        }, 1500);
    }

    private void toHome() {

        // 判断是否是第一次开启应用
        boolean isFirstOpened = SPUtils.getInstance().getBoolean(SPUtils.FIRST_OPENED, false);
        int appVersion = SPUtils.getInstance().getInt(SPUtils.SP_APP_VERSION,0);

        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpened || appVersion < AppUtils.getVersionCodeInt()) {
            SPUtils.getInstance().putBoolean(SPUtils.FIRST_OPENED, true);
            SPUtils.getInstance().putInt(SPUtils.SP_APP_VERSION,AppUtils.getVersionCodeInt());

            startActivity(new Intent(this, WelcomeActivity.class));
            overridePendingTransition(R.anim.alpha_out, R.anim.alpha_in);// 淡出淡入动画效果
            finish();
            return;
        }

        Intent intent = new Intent(this, HomeActivity.class);

//        Intent intent = new Intent(this, GuideDetailActivity.class);
//        intent.putExtra(GuideDetailActivity.GUIDEID, 4);

        startActivity(intent);

        this.finish();
    }


    //---------------start权限-----------------

    public static final int BASE_VALUE_PERMISSION = 0X0001;
    public static final int PERMISSION_REQ_ID_CAMERA = BASE_VALUE_PERMISSION + 1;
    public static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = BASE_VALUE_PERMISSION + 2;
    public static final int PERMISSION_REQ_ID_ACCESS_COARSE_LOCATION = BASE_VALUE_PERMISSION + 3;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                boolean checkPermissionResult = checkSelfPermissions();
                LogUtil.e("checkPermissionResult: " + checkPermissionResult);

                if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
                    // so far we do not use OnRequestPermissionsResultCallback
                }
            }
        }, 500);
    }

    /**
     * RECORD_AUDIO 录音
     * WRITE_EXTERNAL_STORAGE sd写权限
     * ACCESS_COARSE_LOCATION 定位
     */
    private boolean checkSelfPermissions() {
        return checkSelfPermission(android.Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)
                && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                PERMISSION_REQ_ID_ACCESS_COARSE_LOCATION)
                && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            return false;
        }
        if (permission == android.Manifest.permission.WRITE_EXTERNAL_STORAGE) {
//            download();
            goHome();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQ_ID_CAMERA: {
                if (grantResults.length > 0) {
                    boolean ischeck = checkSelfPermission(android.Manifest.permission
                            .ACCESS_COARSE_LOCATION, PERMISSION_REQ_ID_ACCESS_COARSE_LOCATION);
                    if (ischeck)
                        checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
                }
                break;
            }
            case PERMISSION_REQ_ID_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0) {
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
                }
                break;
            }
            case PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {//选择了不再提示按钮
                    showAccreditDialog();
                    return;
                }
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        download();
                        goHome();
                    } else {
                        checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
                    }
                }
                break;
            }
        }
    }

    //----------------end权限--------------


    //----------start 权限不再询问处理-------------

    private void showAccreditDialog() {
        new AlertDialog.Builder(this)
                .setMessage("温馨提示\n" +
                        "您需要同意潮尚玩使用【储存】权限才能正常使用那就走旅游，" +
                        "由于您选择了【禁止（不再提示）】，将导致无法使用那就走旅游，" +
                        "需要到设置页面手动授权开启【存储】权限，才能继续使用。")
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户至设置页手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName
                                (), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户手动授权，权限请求失败
                        finish();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //引导用户手动授权，权限请求失败
            }
        }).show();
    }

    public static final int REQUEST_PERMISSION_SETTING = 6;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
        }
    }
    //----------end 权限不再询问处理-------------
}
