package com.njz.letsgoapp.util;

import android.content.Context;
import android.os.Environment;

/**
 * Created by LGQ
 * Time: 2018/7/18
 * Function:
 */

public class CommonUtil {

    /**
     * 获取cache路径
     * @param context
     * @return
     */
    public static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }
}
