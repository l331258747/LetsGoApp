package com.njz.letsgoapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function: 获取application context
 */
public class AppUtils {

	private static Context context;

	private AppUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 初始化工具类
	 *
	 * @param context 上下文
	 */
	public static void init(Context context) {
		AppUtils.context = context.getApplicationContext();
	}

	/**
	 * 获取ApplicationContext
	 *
	 * @return ApplicationContext
	 */
	public static Context getContext() {
		if (context != null) return context;
		throw new NullPointerException("u should init first");
	}


	/**
	 * 得到版本号code
	 */
	public static String getVersionCode() {
		if(getPackageInfo() == null) return null;
		return String.valueOf(getPackageInfo().versionCode);
	}

	/**
	 * 得到版本name
	 */
	public static String getVersionName() {
		if(getPackageInfo() == null) return null;
		return String.valueOf(getPackageInfo().versionName);
	}

	/**
	 * 得到包名
	 */
	public static String getPakgeName() {
		if(getPackageInfo() == null) return null;
		return getPackageInfo().packageName;
	}

	private static PackageInfo getPackageInfo() {
		try {
			PackageManager packageManager = getContext().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
			return packageInfo;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int dip2px(float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int px2dip(float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2sp(float pxValue) {
		final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int sp2px(float spValue) {
		final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	//获取状态栏高度
	public static int getStateBar(){
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	//通过反射获取状态栏高度
	public static int getStatusBarHeight() {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			return context.getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}