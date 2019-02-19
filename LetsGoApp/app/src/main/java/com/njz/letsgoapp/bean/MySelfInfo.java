package com.njz.letsgoapp.bean;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.util.GsonUtil;
import com.njz.letsgoapp.util.SPUtils;
import com.njz.letsgoapp.util.jpush.JpushAliasUtil;
import com.njz.letsgoapp.util.log.LogUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by LGQ
 * Time: 2018/8/24
 * Function:
 */

public class MySelfInfo {

    private String userToken;

    private MySelfInfo() {
    }

    private final static class HolderClass {
        private final static MySelfInfo INSTANCE = new MySelfInfo();
    }

    public static MySelfInfo getInstance() {
        return HolderClass.INSTANCE;
    }

    public boolean isLogin() {
        if (!TextUtils.isEmpty(getUserToken())) {
            return true;
        }
        return false;
    }


    public void setData(LoginModel model) {
        LoginInfoModel infoModel = model.getTravelZoneVO();

        SPUtils.getInstance().putString(SPUtils.SP_USER_TOKEN, model.getToken());
        SPUtils.getInstance().putInt(SPUtils.SP_USER_FANS, infoModel.getFansCount());
        SPUtils.getInstance().putInt(SPUtils.SP_USER_FOCUS, infoModel.getFocusCount());
        SPUtils.getInstance().putInt(SPUtils.SP_USER_GENDER, infoModel.getGender());
        SPUtils.getInstance().putString(SPUtils.SP_USER_BIRTHDAY, infoModel.getBirthday());
        SPUtils.getInstance().putInt(SPUtils.SP_USER_USERLEVEL, infoModel.getUserLevel());
        SPUtils.getInstance().putString(SPUtils.SP_USER_NICKNAME, infoModel.getNickname());
        SPUtils.getInstance().putString(SPUtils.SP_USER_NAME, infoModel.getName());
        SPUtils.getInstance().putString(SPUtils.SP_USER_MOBILE, infoModel.getMobile());
        SPUtils.getInstance().putString(SPUtils.SP_USER_AVATAR, infoModel.getAvatar());
        SPUtils.getInstance().putString(SPUtils.SP_USER_LOCAL_AREA, infoModel.getLocalArea());
        SPUtils.getInstance().putString(SPUtils.SP_USER_HOME_AREA, infoModel.getHomeArea());
        SPUtils.getInstance().putString(SPUtils.SP_USER_PERSONAL_STATEMENT, infoModel.getPersonalStatement());
        SPUtils.getInstance().putString(SPUtils.SP_USER_IMG_URL, infoModel.getImgUrl());
        SPUtils.getInstance().putString(SPUtils.SP_USER_LABELS, infoModel.getTravelMacroEntitys());
        SPUtils.getInstance().putInt(SPUtils.SP_USER_ID, infoModel.getUserId());
        SPUtils.getInstance().putString(SPUtils.SP_CUSTOMER_MOBILE, infoModel.getCustomerMobile());
        SPUtils.getInstance().putString(SPUtils.SP_USER_FREE_LABELS, infoModel.getFreeLabel());
        SPUtils.getInstance().putString(SPUtils.SP_USER_BACKIMG, infoModel.getBackImg());
    }

    public void setImLogin(boolean isLogin) {
        SPUtils.getInstance().putBoolean(SPUtils.SP_IM_LOGIN, isLogin);
    }

    public boolean getImLogin(){
        return SPUtils.getInstance().getBoolean(SPUtils.SP_IM_LOGIN);
    }

    public String getUserBackimg(){
        return SPUtils.getInstance().getString(SPUtils.SP_USER_BACKIMG);
    }

    public void setUserBackimg(String str){
        SPUtils.getInstance().putString(SPUtils.SP_USER_BACKIMG, str);
    }

    public String getFreeLabels() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_FREE_LABELS);
    }

    public void setFreeLabels(List<LabelItemModel> labels){
        List<String> lists = new ArrayList<>();
        for(LabelItemModel item : labels){
            lists.add(item.getName());
        }
        SPUtils.getInstance().putString(SPUtils.SP_USER_FREE_LABELS, GsonUtil.convertVO2String(lists));
    }

    public String getCustomerMobile(){
        return SPUtils.getInstance().getString(SPUtils.SP_CUSTOMER_MOBILE);
    }

    public String getDefaultCity(){
        return SPUtils.getInstance().getString(SPUtils.SP_DEFAULT_CITY);
    }

    public void setDefaultCity(String defaultCity){
        SPUtils.getInstance().putString(SPUtils.SP_DEFAULT_CITY, defaultCity);
    }

    public int getUserId(){
        return SPUtils.getInstance().getInt(SPUtils.SP_USER_ID);
    }

    public String getImId(){
        return "u_"+SPUtils.getInstance().getInt(SPUtils.SP_USER_ID);
    }

    public void setLabels(List<LabelItemModel> labels) {
        SPUtils.getInstance().putString(SPUtils.SP_USER_LABELS, GsonUtil.convertVO2String(labels));
    }

    public List<LabelItemModel> getLabels() {
        return GsonUtil.convertString2Collection(SPUtils.getInstance().getString(SPUtils.SP_USER_LABELS), new TypeToken<List<LabelItemModel>>() {
        });
    }

    public String getUserToken() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_TOKEN);
    }

    public int getUserFans() {
        return SPUtils.getInstance().getInt(SPUtils.SP_USER_FANS);
    }

    public void setUserFans(int fans){
        SPUtils.getInstance().putInt(SPUtils.SP_USER_FANS, fans);
    }

    public int getUserFocus() {
        return SPUtils.getInstance().getInt(SPUtils.SP_USER_FOCUS);
    }

    public void setUserFocus(int focus){
        SPUtils.getInstance().putInt(SPUtils.SP_USER_FOCUS, focus);
    }

    public String getUserGender() {
        return SPUtils.getInstance().getInt(SPUtils.SP_USER_GENDER) == 2?"女":"男";
    }

    public void setUserGender(String gender) {
        SPUtils.getInstance().putInt(SPUtils.SP_USER_GENDER, TextUtils.equals("女",gender)?2:1);
    }

    public String getUserBirthday() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_BIRTHDAY);
    }

    public void setUserBirthday(String birthday) {
        SPUtils.getInstance().putString(SPUtils.SP_USER_BIRTHDAY, birthday);
    }

    public int getUserLevel() {
        return SPUtils.getInstance().getInt(SPUtils.SP_USER_USERLEVEL);
    }

    public String getUserNickname() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_NICKNAME);
    }

    public void setUserNickname(String nickname) {
        SPUtils.getInstance().putString(SPUtils.SP_USER_NICKNAME, nickname);
    }

    public String getUserName() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_NAME);
    }

    public void setUserName(String name) {
        SPUtils.getInstance().putString(SPUtils.SP_USER_NAME,name);
    }

    public String getUserMoble() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_MOBILE);
    }

    public void setUserMoble(String moble){
        SPUtils.getInstance().putString(SPUtils.SP_USER_MOBILE,moble);
    }

    public String getUserAvatar() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_AVATAR);
    }

    public String getUserLocalArea() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_LOCAL_AREA);
    }

    public String getUserHomeArea() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_HOME_AREA);
    }

    public String getUserStatement() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_PERSONAL_STATEMENT);
    }

    public void setUserStatement(String statement){
        SPUtils.getInstance().putString(SPUtils.SP_USER_PERSONAL_STATEMENT,statement);
    }

    public String getUserImgUrl() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_IMG_URL);
    }

    public void setUserImgUrl(String str){
        SPUtils.getInstance().putString(SPUtils.SP_USER_IMG_URL,str);
    }

    //---------搜索 start
    public void setSearchCity(List<String> lists){
        if(lists == null) return;
        SPUtils.getInstance().putString(SPUtils.SP_SEARCH_CITY, GsonUtil.convertVO2String(lists));
    }

    public void addSearchCity(String str){
        List<String> results = new ArrayList<>();

        List<String> lists = getSearchCity();
        for (String item : lists){
            if(TextUtils.equals(str,item)){
                lists.remove(item);
                break;
            }
        }
        
        while (lists.size() > 5){
            lists.remove(lists.size() - 1);
        }

        results.add(str);
        results.addAll(lists);
        setSearchCity(results);
    }

    public List<String> getSearchCity(){
        if(TextUtils.isEmpty(SPUtils.getInstance().getString(SPUtils.SP_SEARCH_CITY)))
            return new ArrayList<>();
        return GsonUtil.convertJson2Array(SPUtils.getInstance().getString(SPUtils.SP_SEARCH_CITY));
    }


    public void setSearchGuide(List<String> lists){
        if(lists == null) return;
        SPUtils.getInstance().putString(SPUtils.SP_SEARCH_GUIDE, GsonUtil.convertVO2String(lists));
    }

    public void addSearchGuide(String str){
        List<String> results = new ArrayList<>();

        List<String> lists = getSearchGuide();
        for (String item : lists){
            if(TextUtils.equals(str,item)){
                lists.remove(item);
                break;
            }
        }

        while (lists.size() > 5){
            lists.remove(lists.size() - 1);
        }

        results.add(str);
        results.addAll(lists);

        setSearchGuide(results);
    }

    public List<String> getSearchGuide(){
        if(TextUtils.isEmpty(SPUtils.getInstance().getString(SPUtils.SP_SEARCH_GUIDE)))
            return new ArrayList<>();
        return GsonUtil.convertJson2Array(SPUtils.getInstance().getString(SPUtils.SP_SEARCH_GUIDE));
    }

    public void setSearchServer(List<String> lists){
        if(lists == null) return;
        SPUtils.getInstance().putString(SPUtils.SP_SEARCH_SERVER, GsonUtil.convertVO2String(lists));
    }

    public void addSearchServer(String str){
        List<String> results = new ArrayList<>();

        List<String> lists = getSearchServer();
        for (String item : lists){
            if(TextUtils.equals(str,item)){
                lists.remove(item);
                break;
            }
        }

        while (lists.size() > 5){
            lists.remove(lists.size() - 1);
        }

        results.add(str);
        results.addAll(lists);

        setSearchServer(results);
    }

    public List<String> getSearchServer(){
        if(TextUtils.isEmpty(SPUtils.getInstance().getString(SPUtils.SP_SEARCH_SERVER)))
            return new ArrayList<>();
        return GsonUtil.convertJson2Array(SPUtils.getInstance().getString(SPUtils.SP_SEARCH_SERVER));
    }

    //---------搜索 end


    public void loginOff() {
        SPUtils.getInstance().logoff();
        JpushAliasUtil.cancleTagAndAlias();

        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                LogUtil.e("im 退出成功");
                MySelfInfo.getInstance().setImLogin(false);
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.e("im 退出失败 logout error" + i + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }
}
