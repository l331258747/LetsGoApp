package com.njz.letsgoapp.bean.server;

import android.os.Parcel;
import android.os.Parcelable;

import com.njz.letsgoapp.bean.order.PayModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public class ServerDetailMedel implements Parcelable{


    /**
     * guideScore : 0
     * titleImg : xxxx
     * gender : 1
     * serveType : 1
     * guideId : 5
     * guideImg : http://pc03h8bbw.bkt.clouddn.com/1/20180731/1639442522501.jpg
     * sign : 11,12,14
     * language : 中文,英语
     * birthTime : 1991-02-02
     * title : 长沙本地土著带你飞 | 带车车
     * renegePriceThree : 7,4
     * score : 0
     * signs : ["熟悉当地","会攀岩","喜欢自驾"]
     * costExplain : xxxxx
     * reviewCount : 0
     * startTime : 2017-07-31
     * id : 4
     * serveFeature : xxxx
     * address : 长沙
     * servePrice : 100.0
     * languages : ["中文","英语"]
     * serveMinNum : 1
     * njzGuideServeFormatEntitys : [{"njzGuideServeFormatDic":"xdpy_cx","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":4,"guideServeFormatName":"车辆类型0","id":14},{"njzGuideServeFormatDic":"xdpy_cx","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":4,"guideServeFormatName":"车辆类型1","id":15},{"njzGuideServeFormatDic":"xdpy_cx","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":4,"guideServeFormatName":"车辆类型2","id":16},{"njzGuideServeFormatDic":"xdpy_yy","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":4,"guideServeFormatName":"语言类型3","id":17}]
     * serveMaxNum : 1
     * customSign :
     * name : sj
     * sellCount : 0
     * commentId : 0
     * serviceAge : 1年
     * age : 90后
     * renegePriceFive : 3,1
     * serveTypeName : xdpy
     * status : 0
     */

    private float guideScore;
    private String titleImg;
    private int gender;
    private int serveType;
    private int guideId;
    private String guideImg;
    private String sign;
    private String language;
    private String birthTime;
    private String title;
    private String renegePriceThree;
    private float score;
    private String costExplain;
    private int reviewCount;
    private String startTime;
    private int id;
    private String serveFeature;
    private String address;
    private float servePrice;
    private int serveMinNum;
    private int serveMaxNum;
    private String customSign;
    private String name;
    private int sellCount;
    private int commentId;
    private String serviceAge;
    private String age;
    private String renegePriceFive;
    private String serveTypeName;
    private int status;
    private List<String> signs;
    private List<String> languages;
    private List<PlayChileMedel> njzGuideServeFormatEntitys;
    private String mobile;


    protected ServerDetailMedel(Parcel in) {
        guideScore = in.readFloat();
        titleImg = in.readString();
        gender = in.readInt();
        serveType = in.readInt();
        guideId = in.readInt();
        guideImg = in.readString();
        sign = in.readString();
        language = in.readString();
        birthTime = in.readString();
        title = in.readString();
        renegePriceThree = in.readString();
        score = in.readFloat();
        costExplain = in.readString();
        reviewCount = in.readInt();
        startTime = in.readString();
        id = in.readInt();
        serveFeature = in.readString();
        address = in.readString();
        servePrice = in.readFloat();
        serveMinNum = in.readInt();
        serveMaxNum = in.readInt();
        customSign = in.readString();
        name = in.readString();
        sellCount = in.readInt();
        commentId = in.readInt();
        serviceAge = in.readString();
        age = in.readString();
        renegePriceFive = in.readString();
        serveTypeName = in.readString();
        status = in.readInt();
        signs = in.createStringArrayList();
        languages = in.createStringArrayList();
        mobile = in.readString();
    }

    public static final Creator<ServerDetailMedel> CREATOR = new Creator<ServerDetailMedel>() {
        @Override
        public ServerDetailMedel createFromParcel(Parcel in) {
            return new ServerDetailMedel(in);
        }

        @Override
        public ServerDetailMedel[] newArray(int size) {
            return new ServerDetailMedel[size];
        }
    };

    public float getGuideScore() {
        return guideScore;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public int getGender() {
        return gender;
    }

    public int getServeType() {
        return serveType;
    }

    public int getGuideId() {
        return guideId;
    }

    public String getGuideImg() {
        return guideImg;
    }

    public String getSign() {
        return sign;
    }

    public String getLanguage() {
        return language;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public String getTitle() {
        return title;
    }

    public String getRenegePriceThree() {
        return renegePriceThree;
    }

    public float getScore() {
        return score;
    }

    public String getCostExplain() {
        return costExplain;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getId() {
        return id;
    }

    public String getServeFeature() {
        return serveFeature;
    }

    public String getAddress() {
        return address;
    }

    public float getServePrice() {
        return servePrice;
    }

    public int getServeMinNum() {
        return serveMinNum;
    }

    public int getServeMaxNum() {
        return serveMaxNum;
    }

    public String getCustomSign() {
        return customSign;
    }

    public String getName() {
        return name;
    }

    public int getSellCount() {
        return sellCount;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getServiceAge() {
        return serviceAge;
    }

    public String getAge() {
        return age;
    }

    public String getRenegePriceFive() {
        return renegePriceFive;
    }

    public String getServeTypeName() {
        return serveTypeName;
    }

    public String getMobile() {
        return mobile;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getSigns() {
        return signs;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<PlayChileMedel> getNjzGuideServeFormatEntitys() {
        return njzGuideServeFormatEntitys;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(guideScore);
        dest.writeString(titleImg);
        dest.writeInt(gender);
        dest.writeInt(serveType);
        dest.writeInt(guideId);
        dest.writeString(guideImg);
        dest.writeString(sign);
        dest.writeString(language);
        dest.writeString(birthTime);
        dest.writeString(title);
        dest.writeString(renegePriceThree);
        dest.writeFloat(score);
        dest.writeString(costExplain);
        dest.writeInt(reviewCount);
        dest.writeString(startTime);
        dest.writeInt(id);
        dest.writeString(serveFeature);
        dest.writeString(address);
        dest.writeFloat(servePrice);
        dest.writeInt(serveMinNum);
        dest.writeInt(serveMaxNum);
        dest.writeString(customSign);
        dest.writeString(name);
        dest.writeInt(sellCount);
        dest.writeInt(commentId);
        dest.writeString(serviceAge);
        dest.writeString(age);
        dest.writeString(renegePriceFive);
        dest.writeString(serveTypeName);
        dest.writeInt(status);
        dest.writeStringList(signs);
        dest.writeStringList(languages);
        dest.writeString(mobile);
    }
}
