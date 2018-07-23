package com.njz.letsgoapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.njz.letsgoapp.MyApplication;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function:
 */

public class NetUtil {

    public static boolean isNetworkConnected() {
        final ConnectivityManager connManager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = connManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;


    }
}
