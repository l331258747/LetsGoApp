package com.njz.letsgoapp.bean.mine;

import android.text.TextUtils;

import com.njz.letsgoapp.util.GsonUtil;
import com.njz.letsgoapp.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class MyInfoData {
    String nickname;
    String name;
    int gender = SPUtils.getInstance().getInt(SPUtils.SP_USER_GENDER);
    String birthday;
    String personalStatement;
    String imgUrl;
    String theLabel;
    String freeLabel;

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getFreeLabel() {
        return freeLabel;
    }

    public void setFreeLabel(List<LabelItemModel> freeLabels) {
        if( freeLabels == null || freeLabels.size() == 0) return;
        List<String> values = new ArrayList<>();
        for (LabelItemModel labelItemModel: freeLabels){
            values.add(labelItemModel.getName());
        }
        this.freeLabel = GsonUtil.convertVO2String(values);
    }

    public String getTheLabel() {
        return theLabel;
    }

    public void setTheLabel(String theLabel) {
        this.theLabel = theLabel;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender == 2?"女":"男";
    }

    public void setGender(String gender) {
        this.gender = TextUtils.equals(gender,"女")?2:1;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPersonalStatement() {
        return personalStatement;
    }

    public void setPersonalStatement(String personalStatement) {
        this.personalStatement = personalStatement;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void clean(){

    }
}