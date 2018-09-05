package com.njz.letsgoapp.bean.login;

import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.util.GsonUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class LoginInfoModel {

    /**
     * focusCount : 0
     * fansCount : 0
     * travelImgsEntitys : []
     * gender : null
     * birthday : null
     * registerTime : 1535957478000
     * lastLoginTime : null
     * lastLoginIp : null
     * userLevel : null
     * nickname : 3705038267
     * status : 0
     * name : null
     * mobile : 13211111111
     * registerIp : 192.168.100.103
     * avatar : null
     * localArea : null
     * homeArea : null
     * theTrade : null
     * theLabel : null
     * personalStatement : null
     * imgUrl : null
     */

    private Integer focusCount;
    private Integer fansCount;
    private Integer gender;
    private String birthday;
    private Integer userLevel;
    private String nickname;
    private String name;
    private String mobile;
    private String avatar;
    private String localArea;
    private String homeArea;
    private List<LabelItemModel> travelMacroEntitys;

    private String personalStatement;
    private String imgUrl;

    public String getTravelMacroEntitys() {
        return GsonUtil.convertVO2String(travelMacroEntitys);
    }

    public int getFocusCount() {
        if(focusCount == null)
            return 0;
        return focusCount;
    }

    public int getFansCount() {
        if(fansCount == null)
            return 0;
        return fansCount;
    }

    public int getGender() {
        if(gender == null)
            return 0;
        return gender;
    }

    public String getBirthday() {
        if(birthday == null)
            return "";
        return birthday;
    }

    public int getUserLevel() {
        if(userLevel == null)
            return 0;
        return userLevel;
    }

    public String getNickname() {
        if(nickname == null)
            return "";
        return nickname;
    }

    public String getName() {
        if(name == null)
            return "";
        return name;
    }

    public String getMobile() {
        if(mobile == null)
            return "";
        return mobile;
    }

    public String getAvatar() {
        if(avatar == null)
            return "";
        return avatar;
    }

    public String getLocalArea() {
        if(localArea == null)
            return "";
        return localArea;
    }

    public String getHomeArea() {
        if(homeArea == null)
            return "";
        return homeArea;
    }

    public String getPersonalStatement() {
        if(personalStatement == null)
            return "";
        return personalStatement;
    }

    public String getImgUrl() {
        return imgUrl;
    }

}
