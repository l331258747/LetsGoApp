package com.njz.letsgoapp.bean.other;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public class WXInfoModel implements Parcelable{

    String platUid;
    String platCode;
    String loginType;
    String mobile;
    String introduction;
    String gender;
    String faceImage;
    String userName;
    String realName;
    String msgCode;

    protected WXInfoModel(Parcel in) {
        platUid = in.readString();
        platCode = in.readString();
        loginType = in.readString();
        mobile = in.readString();
        introduction = in.readString();
        gender = in.readString();
        faceImage = in.readString();
        userName = in.readString();
        realName = in.readString();
        msgCode = in.readString();
    }

    public static final Creator<WXInfoModel> CREATOR = new Creator<WXInfoModel>() {
        @Override
        public WXInfoModel createFromParcel(Parcel in) {
            return new WXInfoModel(in);
        }

        @Override
        public WXInfoModel[] newArray(int size) {
            return new WXInfoModel[size];
        }
    };

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getPlatUid() {
        return platUid;
    }

    public void setPlatUid(String platUid) {
        this.platUid = platUid;
    }

    public String getPlatCode() {
        return platCode;
    }

    public void setPlatCode(String platCode) {
        this.platCode = platCode;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if(TextUtils.equals(gender,"女")){//QQ 女为“女”
            this.gender = "2";
        }else if(TextUtils.equals(gender,"男")){
            this.gender = "1";
        }else if(TextUtils.equals(gender,"1")){//微信 男为1，
            this.gender = "1";
        }else {
            this.gender = "2";
        }
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public WXInfoModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(platUid);
        dest.writeString(platCode);
        dest.writeString(loginType);
        dest.writeString(mobile);
        dest.writeString(introduction);
        dest.writeString(gender);
        dest.writeString(faceImage);
        dest.writeString(userName);
        dest.writeString(realName);
        dest.writeString(msgCode);
    }
}
