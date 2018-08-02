package com.njz.letsgoapp;

/**
 * Created by LGQ
 * Time: 2018/8/2
 * Function:
 */

import android.content.Context;
import android.os.Process;
import android.support.annotation.Keep;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //Dalvik在5.0之前，为每一个APK只生成一个classes.dex，
        // 所以会有上述所说的方法数超限的问题，如果我们可以将一个dex文件分成多个，
        // 在应用启动时，加载第一个（主dex）dex文件，当启动以后，再依次加载其他dex文件。这样就可以规避上述问题了。
        // MultiDex即是实现了这样的功能
        //如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this);
        initSophix();
    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("25006830"
                        , "1fee88db286d19f65bfa501a01aaf374"
                        , "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCYqw50NZQ3Tsi5+DAuVjP0EuQEXKsul6BPTJVeoWRiqA85vdGpCdoMlQImRT2p1iQxNwoBJVSnssLvkuO7XqVoTU9WOHEbwXxTh1Bft4ZhpxPTob+GmYWgvR5k26AS4noIRKnaWjEX7u9V2RW6L+0FAc+OPSGcYE5wwQkJzO3Nw0BqSt8TpNnRE7gmjDk9wBxHEg1KK0rECZlYkEPlJF9Z1MUQRBdzyjsVb/+2XnNQjCdvbzg2aL2Ce+rIpA+9ZUrOkET8Zsxtt14oDFEo4bEoZ/TJ3UMNJj4Wi6wV1xX3wt3n+D8Ekw6vOU8XIg144m9/TdIc+LLTYRyw56ungeCvAgMBAAECggEAH/q07GLMyUkXgt0aOpqUB2xtlvxSX1AjpHjGUSw9euz5Q38fi+kkY75S1PFsGUdiDW0aC2NmeDpZ2xVJ3tXUkCShmc3443eEM3sEuwgWfh4haRNYqzk9ZI4476eRwGLwbQr1CWgbIIhMWg8/HeEsJQBcGc90Wu1RgQzud78VkSQhLqfjaoaxb200nRaAGoiOUcgV+HtFYMnuddx8OvpiQ0BG/DTyWLRYAG+JPoQCY1w7nMfKGSJpexw+KjARUCvfVh2SnWIs6bpQpvFb62sGHmSOTcbJ0dcuV/GqLp137UV5bAaeiZuVBM7FZva1QwJunpgBefy7Ri704neJJysbAQKBgQDqsRfK1YEjxBpcqZNDrEdpJuNEUXh7cFQhQcO6sshMvhb6Kf4Hzw5Q7dNg5pIOLneunrfRkvzypFE+fZ53I9ewdCR0R+I2CTm47bjUVU8GVWZnfqfic94EjIdxTEyZNGHW6CXpUrFNbvk25THLoQwQ0MZmGoY/L4erNQyfwf6o7QKBgQCmh4BRXPs1Vv6nFO7d+lXshJ0JaJA9MzMYnXUTM7polSFBtzUUwnI0W/vq7pThtrFuzRKSx5a1bnhfynVFDV9dcvHrDjzJfWtAWxvwDoU2SPX6J5uTdXWSxH8eI4FqEGgQOILFlCeZQynTKkDjFEM+cFhHRelnDeCyNAQBLe/IiwKBgAXCIptPccO25A6x+nMGnxntYlYiyZn9v5KxUmQaTt/TKi0pKpp2cht6Ol1+SsceTMF0E36I5LvvXC70VhcGQhyKPPq9aMd6onfvHUHYdf/pyIFxkhz0StZBvHEDNj3sExp6x7NZ3A/SUTsPlJEyemFPPg7zR6f1OpbwzkZ9NAphAoGBAIE5lPGOXcG+iE3PiKzondSz88hhQ8y59mSbi0YjVfvh/bSpHp/FZCoNVyevjAi7ZGS+7jLIfsK7vt1WljnHbsFteR0WJ0WT9a4zBTL7kZPWekpsgeWjuGDz6lDQ7JQJtzcfIxt/JFtiKwqaN0//YU8KjvZ/4eb+debTRmhD5VaRAoGBAJj2hdQ7Vu7tdc6yNXUeeUpaQ1chhTzqvcPO0QIM5JqukLGpe7FO4tgzz3L5Wvf6IkE1skl/U6Mh9jSt0vZtY2+UNLh6lHK34EPuEINTk1FnLbWwM7ijUTRjvylYS480sSp9vkpIBrnYC7H988dnp0MD6bcyUKxFpEVsgv1WDQMU")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
//                            instance.killProcessSafely();
                        }
                    }
                }).initialize();
    }
}

/*
 //兼容老版本的code说明
    int CODE_LOAD_SUCCESS = 1;//加载阶段, 成功
    int CODE_ERR_INBLACKLIST = 4;//加载阶段, 失败设备不支持
    int CODE_REQ_NOUPDATE = 6;//查询阶段, 没有发布新补丁
    int CODE_REQ_NOTNEWEST = 7;//查询阶段, 补丁不是最新的
    int CODE_DOWNLOAD_SUCCESS = 9;//查询阶段, 补丁下载成功
    int CODE_DOWNLOAD_BROKEN = 10;//查询阶段, 补丁文件损坏下载失败
    int CODE_UNZIP_FAIL = 11;//查询阶段, 补丁解密失败
    int CODE_LOAD_RELAUNCH = 12;//预加载阶段, 需要重启
    int CODE_REQ_APPIDERR = 15;//查询阶段, appid异常
    int CODE_REQ_SIGNERR = 16;//查询阶段, 签名异常
    int CODE_REQ_UNAVAIABLE = 17;//查询阶段, 系统无效
    int CODE_REQ_SYSTEMERR = 22;//查询阶段, 系统异常
    int CODE_REQ_CLEARPATCH = 18;//查询阶段, 一键清除补丁
    int CODE_PATCH_INVAILD = 20;//加载阶段, 补丁格式非法
    //查询阶段的code说明
    int CODE_QUERY_UNDEFINED = 31;//未定义异常
    int CODE_QUERY_CONNECT = 32;//连接异常
    int CODE_QUERY_STREAM = 33;//流异常
    int CODE_QUERY_EMPTY = 34;//请求空异常
    int CODE_QUERY_BROKEN = 35;//请求完整性校验失败异常
    int CODE_QUERY_PARSE = 36;//请求解析异常
    int CODE_QUERY_LACK = 37;//请求缺少必要参数异常
    //预加载阶段的code说明
    int CODE_PRELOAD_SUCCESS = 100;//预加载成功
    int CODE_PRELOAD_UNDEFINED = 101;//未定义异常
    int CODE_PRELOAD_HANDLE_DEX = 102;//dex加载异常
    int CODE_PRELOAD_NOT_ZIP_FORMAT = 103;//基线dex非zip格式异常
    int CODE_PRELOAD_REMOVE_BASEDEX = 105;//基线dex处理异常
    //加载阶段的code说明 分三部分dex加载, resource加载, lib加载
    //dex加载
    int CODE_LOAD_UNDEFINED = 71;//未定义异常
    int CODE_LOAD_AES_DECRYPT = 72;//aes对称解密异常
    int CODE_LOAD_MFITEM = 73;//补丁SOPHIX.MF文件解析异常
    int CODE_LOAD_COPY_FILE = 74;//补丁拷贝异常
    int CODE_LOAD_SIGNATURE = 75;//补丁签名校验异常
    int CODE_LOAD_SOPHIX_VERSION = 76;//补丁和补丁工具版本不一致异常
    int CODE_LOAD_NOT_ZIP_FORMAT = 77;//补丁zip解析异常
    int CODE_LOAD_DELETE_OPT = 80;//删除无效odex文件异常
    int CODE_LOAD_HANDLE_DEX = 81;//加载dex异常
    // 反射调用异常
    int CODE_LOAD_FIND_CLASS = 82;
    int CODE_LOAD_FIND_CONSTRUCTOR = 83;
    int CODE_LOAD_FIND_METHOD = 84;
    int CODE_LOAD_FIND_FIELD = 85;
    int CODE_LOAD_ILLEGAL_ACCESS = 86;
    //resource加载
    public static final int CODE_LOAD_RES_ADDASSERTPATH = 123;//新增资源补丁包异常
    //lib加载
    int CODE_LOAD_LIB_UNDEFINED = 131;//未定义异常
    int CODE_LOAD_LIB_CPUABIS = 132;//获取primaryCpuAbis异常
    int CODE_LOAD_LIB_JSON = 133;//json格式异常
    int CODE_LOAD_LIB_LOST = 134;//lib库不完整异常
    int CODE_LOAD_LIB_UNZIP = 135;//解压异常
    int CODE_LOAD_LIB_INJECT = 136;//注入异常
 */