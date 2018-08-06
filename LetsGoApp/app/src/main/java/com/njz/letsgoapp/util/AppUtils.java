package com.njz.letsgoapp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


}