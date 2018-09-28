package com.njz.letsgoapp.bean.mine;

import android.text.TextUtils;

public class MyInfoData {
    String nickname;
    String name;
    int gendar ;
    String birthday;
    String personalStatement;
    String imgUrl;
    String theLabel;

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

    public String getGendar() {
        return gendar == 0?"女":"男";
    }

    public void setGendar(String gendar) {
        this.gendar = TextUtils.equals(gendar,"女")?0:1;
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