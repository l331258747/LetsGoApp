package com.njz.letsgoapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function: Preferences 工具类
 */
public class PreferencesUtils {

    public static final String FIRST_OPENED = "first_open";


    public static final String SP_USER_TOKEN = "sp_user_token";

    public static final String SP_USER_ID = "sp_user_id";
    public static final String SP_USER_PWD = "sp_user_pwd";
    public static final String SP_USER_NICK = "sp_user_nick";
    public static final String SP_USER_AVATAR = "sp_user_avatar";
    public static final String SP_USER_TELPHONE = "sp_user_telphone";
    public static final String SP_USER_EMAIL = "sp_user_email";
    public static final String SP_USER_SEX = "sp_user_sex";
    public static final String SP_USER_BIRTHDAY = "sp_user_birthday";
    public static final String SP_USER_POINTS = "sp_user_points";
    public static final String SP_USER_ISORDER = "sp_user_isorder";
    public static final String SP_USER_ISGROUP = "sp_user_isgroup";

    public static final String PREF_COOKIES = "pref_cookies";
    public static final String SP_SEARCH_HIS = "pref_search_his";

    public static final String SP_USER_DEALER = "sp_user_dealer";
    public static final String SP_USER_DEALERID = "sp_user_dealerid";
    public static final String SP_USER_BIND = "sp_user_bind";
    public static final String SP_USER_SIGN = "sp_user_sign";
    public static final String SP_USER_COLLECT_NUM = "sp_user_collect_num";
    public static final String SP_USER_COUNPON_NUM = "sp_user_coupon_num";
    public static final String SP_CITY_ID = "sp_user_city_id";
    public static final String SP_CITY_NAME = "sp_user_city_name";
    public static final String SP_CITY_SHORT_NAME = "sp_user_city_short_name";


    private static SharedPreferences mSharedPreferences;
    private static PreferencesUtils mPreferenceUtils;
    private static SharedPreferences.Editor editor;

    public static final String PREFERENCE_NAME = "com.njz.letsgo_preferences";


    public static synchronized void init(Context cxt) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new PreferencesUtils(cxt);
        }
    }

    private PreferencesUtils(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static PreferencesUtils getInstance() {
        if (mPreferenceUtils == null) {
            throw new RuntimeException("please init first!");
        }
        return mPreferenceUtils;
    }


    public boolean isLogin() {
        if (mSharedPreferences.contains(SP_USER_ID)) {
            if (getInt(SP_USER_ID) > 0) {
                return true;
            }
        }
        return false;
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
