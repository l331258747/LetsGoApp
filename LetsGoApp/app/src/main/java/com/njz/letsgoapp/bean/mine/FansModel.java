package com.njz.letsgoapp.bean.mine;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class FansModel {


    /**
     * userId : 3
     * imgUrl : null
     * nickname : clb
     * userLevel : 23
     * focusTime : 1536143905000
     * focusTimeToString : 16小时前
     */

    private int userId;
    private String imgUrl;
    private String nickname;
    private int userLevel;
    private String focusTime;
    private String focusTimeToString;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImgUrl() {
        if(imgUrl == null)
            return "";
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getFocusTime() {
        return focusTime;
    }

    public void setFocusTime(String focusTime) {
        this.focusTime = focusTime;
    }

    public String getFocusTimeToString() {
        return focusTimeToString;
    }

    public void setFocusTimeToString(String focusTimeToString) {
        this.focusTimeToString = focusTimeToString;
    }
}
