package com.njz.letsgoapp.util;

import android.app.Activity;
import android.os.Build;

/**
 * Created by jinty on 2020/9/27.
 */

public class HasActivity {

    /**
     * 判断Activity是否Destroy
     * @param mActivity
     * @return
     */
    public static boolean isDestroy(Activity mActivity) {
        if (mActivity== null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }
}
