package com.njz.letsgoapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function:SharedPreferences 工具类
 */
public class SPUtils {

    public static final String FIRST_OPENED = "first_open";


    public static final String SP_USER_TOKEN = "sp_user_token";
    public static final String SP_USER_FANS = "sp_user_fans";
    public static final String SP_USER_FOCUS = "sp_user_focus";
    public static final String SP_USER_GENDER = "sp_user_gender";
    public static final String SP_USER_BIRTHDAY = "sp_user_birthday";
    public static final String SP_USER_USERLEVEL = "sp_user_userLevel";
    public static final String SP_USER_NICKNAME = "sp_user_nickname";
    public static final String SP_USER_NAME = "sp_user_name";
    public static final String SP_USER_MOBILE = "sp_user_mobile";
    public static final String SP_USER_AVATAR = "sp_user_avatar";
    public static final String SP_USER_LOCAL_AREA = "sp_user_localArea";
    public static final String SP_USER_HOME_AREA = "sp_user_homeArea";
    public static final String SP_USER_PERSONAL_STATEMENT = "sp_user_personalStatement";
    public static final String SP_USER_IMG_URL = "sp_user_imgUrl";




    private static SharedPreferences mSharedPreferences;
    private static SPUtils mPreferenceUtils;
    private static SharedPreferences.Editor editor;

    public static final String PREFERENCE_NAME = "com.njz.letsgo_preferences";


    public static synchronized void init(Context cxt) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new SPUtils(cxt);
        }
    }

    private SPUtils(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static SPUtils getInstance() {
        if (mPreferenceUtils == null) {
            throw new RuntimeException("please init first!");
        }
        return mPreferenceUtils;
    }


    public boolean putString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key,
                            String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public boolean putInt(String key, int value) {
        editor.putInt(key, value);
        return editor.commit();
    }


    public int getInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return getInt(key, defaultValue);
    }

    public boolean putLong(String key, long value) {
        editor.putLong(key, value);
        return editor.commit();
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }


    public long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public boolean putFloat(String key, float value) {
        editor.putFloat(key, value);
        return editor.commit();
    }


    public float getFloat(String key) {
        return getFloat(key, -1);
    }


    public float getFloat(String key, float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public boolean putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }


    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }


    public boolean getBoolean(String key,
                              boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }


    public void put(String key, @Nullable Set<String> values) {
        editor.putStringSet(key, values).apply();
    }


    public Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }


    public Set<String> getStringSet(String key, @Nullable Set<String> defaultValue) {
        return mSharedPreferences.getStringSet(key, defaultValue);
    }


    public void remove(String key) {
        editor.remove(key).apply();
    }

    public void clearAll() {
        editor.clear().commit();
    }

    public void logoff() {
//		remove(SP_USER_ID);
//		remove(SP_USER_PWD);
//		remove(SP_USER_TELPHONE);

        remove(SP_USER_TOKEN);


    }
}
