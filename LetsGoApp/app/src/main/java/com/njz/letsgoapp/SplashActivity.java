package com.njz.letsgoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.SPUtils;
import com.njz.letsgoapp.util.jpush.JpushAliasUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.view.im.cache.UserCacheManager;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by LGQ
 * Time: 2018/8/21
 * Function:
 */

public class SplashActivity extends BaseActivity {

    ImageView iv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setIv();

        hideBottomUIMenu();

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        initUserInfo();

    }

    public void setIv(){
        iv = $(R.id.iv);
        switch (AppUtils.getManifestValue("UMENG_CHANNEL")){
            case "xiaomi":
                iv.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.splash_xiaomi));
                break;
            case "sougou":
                iv.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.splash_sougou));
                break;
            case "meizu":
                iv.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.splash_meizu));
                break;
            case "huawei":
                iv.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.splash_huawei));
                break;
            case "ali":
                iv.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.splash_ali));
                break;
            case "c360":
                iv.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.splash_360));
                break;
            default:
                iv.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.splash));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        hideTitleLayout();
        EMClient.getInstance().chatManager().loadAllConversations();
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

        setImLodin();

        if(AppUtils.getManifestValue("UMENG_CHANNEL").equalsIgnoreCase("xiaomi") && Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")){
            SPUtils.getInstance().putBoolean(SPUtils.FIRST_OPENED, true);
            SPUtils.getInstance().putInt(SPUtils.SP_APP_VERSION,AppUtils.getVersionCodeInt());
        }

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
        startActivity(intent);

        this.finish();
    }

    //im登录
    public void setImLodin(){


        if(!MySelfInfo.getInstance().isLogin() || MySelfInfo.getInstance().getImLogin()) return;
        //只有app登录，im未登录才进入下面im登录流程

        UserCacheManager.save(MySelfInfo.getInstance().getImId(),MySelfInfo.getInstance().getUserNickname(),MySelfInfo.getInstance().getUserImgUrl());

        LogUtil.e("getRegistrationID:" + JPushInterface.getRegistrationID(context));
        JpushAliasUtil.setTagAndAlias();

        if (AppUtils.getVersionCodeInt() % 100 != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(MySelfInfo.getInstance().getImId(), Constant.IM_PASSWORD);
                        LogUtil.e("im 注册成功");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        int errorCode = e.getErrorCode();
                        String message = e.getMessage();
                        LogUtil.e("im 注册失败");
                        LogUtil.e("errorCode:" + errorCode);
                        LogUtil.e("message:" + message);
                    }
                }
            }).start();
        }

        EMClient.getInstance().login(MySelfInfo.getInstance().getImId(), Constant.IM_PASSWORD, new EMCallBack() {
            @Override
            public void onSuccess() {
                // 加载所有会话到内存
                EMClient.getInstance().chatManager().loadAllConversations();
                LogUtil.e("im 登录成功");

                MySelfInfo.getInstance().setImLogin(true);
            }

            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e("im 登录失败 code: " + i + ",message: " + s);
                        LogUtil.e("code: " + i + ",message: " + s);
                        MySelfInfo.getInstance().setImLogin(false);
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    //---------------start权限-----------------

    public static final int BASE_VALUE_PERMISSION = 0X0001;
    public static final int PERMISSION_REQ_ID_CAMERA = BASE_VALUE_PERMISSION + 1;
    public static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = BASE_VALUE_PERMISSION + 2;
    public static final int PERMISSION_REQ_ID_ACCESS_COARSE_LOCATION = BASE_VALUE_PERMISSION + 3;
    public static final int PERMISSION_REQ_ID_ACCESS_COARSE_AUDIO = BASE_VALUE_PERMISSION + 4;

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
                && checkSelfPermission(android.Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_ACCESS_COARSE_AUDIO)
                && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, PERMISSION_REQ_ID_ACCESS_COARSE_LOCATION)
                && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
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
                    boolean ischeck0 = checkSelfPermission(android.Manifest.permission
                            .RECORD_AUDIO, PERMISSION_REQ_ID_ACCESS_COARSE_AUDIO);
                    boolean ischeck = checkSelfPermission(android.Manifest.permission
                            .ACCESS_COARSE_LOCATION, PERMISSION_REQ_ID_ACCESS_COARSE_LOCATION);
                    if (ischeck0 && ischeck)
                        checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
                }
                break;
            }
            case PERMISSION_REQ_ID_ACCESS_COARSE_AUDIO:{
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
