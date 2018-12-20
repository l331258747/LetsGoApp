package com.njz.letsgoapp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.SPUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.wxapi.UpgradeDialogListener;
import com.taobao.sophix.SophixManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;

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

        //本地日志获取和bugly有冲突
//        ExceptionCrashHandler.getInstance().init(context);

        JPushInterface.init(context);
        JPushInterface.setDebugMode(true);

        SPUtils.init(context);
        AppUtils.init(this);
        LogUtil.setShowLog(true);

        registToWX();

        initBugly();

        //sophix 热修复
        SophixManager.getInstance().queryAndLoadNewPatch();

        initYouMeng();

    }

    private void initYouMeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * qq、baidu、xiaomi、huawei、ali、vivo、oppo、meizu、c360 qq代表应用宝
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
         */
//        UMConfigure.init(context, "5c1740f6b465f561b40002cc", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, "");

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        if(AppUtils.getVersionCodeInt() % 100 == 0){
            UMConfigure.setLogEnabled(false);
        }else{
            UMConfigure.setLogEnabled(true);
        }

    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constant.WEIXIN_APP_ID);

    }

    private void initBugly(){

        /***** Beta高级设置 *****/
        //true表示app启动自动初始化升级模块; false不会自动初始化; 开发者如果担心sdk初始化影响app启动速度，可以设置为false， 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
        Beta.autoInit = true;
        //true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
        Beta.autoCheckUpgrade = true;
        //设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
        Beta.upgradeCheckPeriod = 60 * 1000;
        //设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
        Beta.initDelay = 3 * 1000;
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        //设置sd卡的Download为更新资源保存目录;后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
       //已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
        Beta.showInterruptedStrategy = true;
        //只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
        Beta.canShowUpgradeActs.add(HomeActivity.class);
        Beta.upgradeDialogLifecycleListener = new UpgradeDialogListener();
        //第三个参数为SDK调试模式开关 建议在测试阶段建议设置成true，发布时设置为false。
//        CrashReport.initCrashReport(getApplicationContext(), "cd379e9015", true);
        Bugly.init(getApplicationContext(), "a6655ed7d5", true);
    }
}
