package com.njz.letsgoapp.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/21.
 */

public class LogUtil {

    public static String tagPrefix = "";
    public static boolean showLog = true;

    public static void setShowLog(boolean showLog) {
        showLog = showLog;
    }

    /**
     * 得到tag（所在类.方法（L:行））
     *
     * @return
     */
    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = stackTraceElement.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "%s.%s(L:%d)";
//        tag = String.format(tag, new Object[]{callerClazzName, stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())});
        tag = String.format(tag, new Object[]{"----LOG-----", stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())});
        //给tag设置前缀
        tag = TextUtils.isEmpty(tagPrefix) ? tag : tagPrefix + ":" + tag;
        return tag;
    }

    public static void v(String msg) {
        if (showLog) {
            String tag = generateTag();
            Log.v(tag, msg);
        }
    }

    public static void v(String msg, Throwable tr) {
        if (showLog) {
            String tag = generateTag();
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String msg) {
        if (showLog) {
            String tag = generateTag();
            Log.d(tag, msg);
        }
    }

    public static void d(String msg, Throwable tr) {
        if (showLog) {
            String tag = generateTag();
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String msg) {
        if (showLog) {
            String tag = generateTag();
            Log.i(tag, msg);
        }
    }

    public static void i(String msg, Throwable tr) {
        if (showLog) {
            String tag = generateTag();
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String msg) {
        if (showLog) {
            String tag = generateTag();
            Log.w(tag, msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (showLog) {
            String tag = generateTag();
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String msg) {
        if (showLog) {
            String tag = generateTag();
            Log.e(tag, msg);
        }
    }

    public static void e(String msg, Throwable tr) {
        if (showLog) {
            String tag = generateTag();
            Log.e(tag, msg, tr);
        }
    }

    public static void wtf(String msg) {
        if (showLog) {
            String tag = generateTag();
            Log.wtf(tag, msg);
        }
    }

    public static void wtf(String msg, Throwable tr) {
        if (showLog) {
            String tag = generateTag();
            Log.wtf(tag, msg, tr);
        }
    }
}
