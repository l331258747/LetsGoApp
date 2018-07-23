package com.njz.letsgoapp.util;

import android.content.Context;
import android.view.WindowManager;

import java.util.regex.Pattern;

public class Utils {

	private static Context context;

	private Utils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 初始化工具类
	 *
	 * @param context 上下文
	 */
	public static void init(Context context) {
		Utils.context = context.getApplicationContext();
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


	//判断，返回布尔值
	public static boolean isPhoneNumber(String input) {
//		String regex = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
//		Pattern p = Pattern.compile(regex);
//		return p.matcher(input).matches();

		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186
		 * 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		String telRegex = "[1][34758]\\d{9}";
//		if (TextUtils.isEmpty(mobiles))
//			return false;
//		else{
//			return Pattern.matches(telRegex, input);
//		}
		return Pattern.matches(telRegex, input);
	}


	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	//获取屏幕高度
	public static int getWindowHeight(){
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}
	//获取屏幕高度
	public static int getWindowWidth(){
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}
}