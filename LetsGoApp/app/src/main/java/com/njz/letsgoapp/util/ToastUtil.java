package com.njz.letsgoapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * toast工具类
 */
public class ToastUtil {

	private static Toast mToast;

	private static void showToast(Context mContext, String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}
		mToast.show();
	}

	public static void showShortToast(Context mContext, String msg){
		showToast(mContext, msg, Toast.LENGTH_SHORT);
	}

	public static void showLongToast(Context mContext, String msg){
		showToast(mContext, msg, Toast.LENGTH_LONG);
	}

}
