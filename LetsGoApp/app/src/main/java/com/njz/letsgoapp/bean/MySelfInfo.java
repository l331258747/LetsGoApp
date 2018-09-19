package com.njz.letsgoapp.bean;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.util.GsonUtil;
import com.njz.letsgoapp.util.SPUtils;

import java.util.List;

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

    public int getUserFocus() {
        return SPUtils.getInstance().getInt(SPUtils.SP_USER_FOCUS);
    }

    public String getUserGender() {
        return SPUtils.getInstance().getInt(SPUtils.SP_USER_GENDER) == 0?"女":"男";
    }

    public void setUserGender(String gendar) {
        SPUtils.getInstance().putInt(SPUtils.SP_USER_GENDER, TextUtils.equals("女",gendar)?0:1);
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


    public void loginOff() {
        SPUtils.getInstance().logoff();
    }


}
