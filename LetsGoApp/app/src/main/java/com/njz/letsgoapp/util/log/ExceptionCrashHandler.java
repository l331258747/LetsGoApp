package com.njz.letsgoapp.util.log;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function: 崩溃日志处理
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    // 单例设计模式
    private static ExceptionCrashHandler mInstance;
    // 留下原来的，便于开发的时候调试
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 上下文  获取版本信息和手机信息
    private Context mContext;

    public static ExceptionCrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (ExceptionCrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }

    private ExceptionCrashHandler() {

    }

    public void init(Context context) {
        /**
         * 官方解释
         * Set the handler invoked when this thread abruptly terminates
         * due to an uncaught exception.
         **/
        Thread.currentThread().setUncaughtExceptionHandler(this);
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.mContext = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        LogUtil.e("捕捉到了异常");
        // 1. 获取信息
        // 1.1 崩溃信息
        // 1.2 手机信息
        // 1.3 版本信息
        // 2.写入文件
        String crashFileName = saveInfoToSD(ex);

        LogUtil.e("fileName --> " + crashFileName);

        // 系统默认处理
        mDefaultHandler.uncaughtException(t, ex);
    }

    /**
     * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
     * @param ex
     * @return
     */
    private String saveInfoToSD(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        sb.append("TIME").append(" = ").append(getAssignTime("yyyy_MM_dd_HH_mm")).append("\n");

        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        sb.append(obtainExceptionInfo(ex) + "\r\n\r\n\r\n");

        File dir = FileUtil.getFolder(Constant.LOG_PATH);

        try {
            fileName = dir.toString() + File.separator + getAssignTime("yyyy_MM_dd") + ".log";
            FileOutputStream fos = new FileOutputStream(fileName,true);
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
        return fileName;
    }

    /**
     * 返回当前日期根据格式
     **/
    private String getAssignTime(String dateFormatStr) {
        DateFormat dataFormat = new SimpleDateFormat(dateFormatStr);
        long currentTime = System.currentTimeMillis();
        return dataFormat.format(currentTime);
    }


    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     *
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);
        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" + Build.PRODUCT);
        map.put("MOBLE_INFO", getMobileInfo());
        return map;
    }


    /**
     * Cell phone information
     *
     * @return
     */
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 获取系统未捕捉的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }


    /**
     * 用来上传服务器后删除操作
     */
    private void delLogFile(){
        FileUtil.delAllFile(FileUtil.getFolder(Constant.LOG_PATH));
    }
}