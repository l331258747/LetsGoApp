package com.njz.letsgoapp.bean.home;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/29
 * Function:
 */

public class GuideDetailModel implements Parcelable{


    /**
     * guideId : 4
     * mobile : 1234511
     * guideAge : null
     * guideName : liyu
     * serviceAge : null
     * language : ["中文",“英文”]
     * sign : ["熟悉当地","会攀岩","喜欢自驾"]
     * cardViable : null
     * guideViable : null
     * driveViable : null
     * guideImg : http://pc03h8bbw.bkt.clouddn.com/1/20180731/1639442522501.jpg
     * guideGender : 1
     * serviceCounts : 1
     * count : 0
     * introduce : 个人简介
     * guideScore : 0.0
     * guideServices : null
     * carConditions : null
     * buyServices : null
     * travelArranges : null
     * travelGuideServiceEntitys : [{"guideId":4,"commentId":null,"titleImg":"11","servePrice":111,"serveFeature":null,"serveType":"私人订制","renegePriceThree":11,"renegePriceFive":11,"costExplain":"11","title":"私人服务带你飞","status":1,"location":"张家界"}]
     * guideStory : null
     * travelFirstReviewVO : {"name":null,"guideService":null,"carCondition":null,"buyService":null,"travelArrange":null,"score":null,"userDate":null,"userContent":null,"img":null,"level":null}
     */

    /**
     * buyServices : 0
     * guideScore : 0
     * image :
     * carConditions : 0
     * driveViable : 1
     * guideAge : 90后
     * guideServices : 0
     * guideViable : 1
     * travelFirstReviewVO : null
     * travelArranges : 0
     * cardViable : 0
     * travelGuideServiceInfoEntitys : []
     * id : 4
     * serviceAge : 1年
     */


    private String mobile;
    private String guideName;
    private List<String> language;
    private List<String> sign;
    private String guideImg;
    private int guideGender;
    private int serviceCounts;
    private int count;
    private String introduce;
    private String guideStory;
    private String image;
    private String guideAge;
    private int id;
    private String serviceAge;
    private List<GuideServiceModel> travelGuideServiceInfoVOs;

    private int driveViable;
    private int guideViable;
    private int cardViable;

    private float guideScore;
    private float travelArranges;
    private float buyServices;
    private float carConditions;
    private float guideServices;

    private String shareImg;
    private String shareContent;
    private String shareTitle;
    private String shareUrl;

    protected GuideDetailModel(Parcel in) {
        mobile = in.readString();
        guideName = in.readString();
        language = in.createStringArrayList();
        sign = in.createStringArrayList();
        guideImg = in.readString();
        guideGender = in.readInt();
        serviceCounts = in.readInt();
        count = in.readInt();
        introduce = in.readString();
        guideStory = in.readString();
        image = in.readString();
        guideAge = in.readString();
        id = in.readInt();
        serviceAge = in.readString();
        travelGuideServiceInfoVOs = in.createTypedArrayList(GuideServiceModel.CREATOR);
        driveViable = in.readInt();
        guideViable = in.readInt();
        cardViable = in.readInt();
        guideScore = in.readFloat();
        travelArranges = in.readFloat();
        buyServices = in.readFloat();
        carConditions = in.readFloat();
        guideServices = in.readFloat();
        shareImg = in.readString();
        shareContent = in.readString();
        shareTitle = in.readString();
        shareUrl = in.readString();
    }

    public static final Creator<GuideDetailModel> CREATOR = new Creator<GuideDetailModel>() {
        @Override
        public GuideDetailModel createFromParcel(Parcel in) {
            return new GuideDetailModel(in);
        }

        @Override
        public GuideDetailModel[] newArray(int size) {
            return new GuideDetailModel[size];
        }
    };

    public String getShareImg() {
        return shareImg;
    }

    public String getShareContent() {
        return shareContent;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public List<GuideServiceModel> getTravelGuideServiceInfoVOs() {
        return travelGuideServiceInfoVOs;
    }

    public void setTravelGuideServiceInfoEntitys(List<GuideServiceModel> travelGuideServiceInfoVOs) {
        this.travelGuideServiceInfoVOs = travelGuideServiceInfoVOs;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }


    public List<String> getLanguage() {
        return language;
    }

    public List<String> getServiceTag(){
        List<String> list = new ArrayList<>();
        if(!TextUtils.isEmpty(guideAge))
            list.add(guideAge);
        if(!TextUtils.isEmpty(serviceAge))
            list.add(serviceAge);
        if(language != null || language.size() > 0)
            list.addAll(language);
        return list;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public List<String> getSign() {
        return sign;
    }

    public void setSign(List<String> sign) {
        this.sign = sign;
    }


    public String getGuideImg() {
        return guideImg;
    }

    public void setGuideImg(String guideImg) {
        this.guideImg = guideImg;
    }

    public int getGuideGender() {
        return guideGender;
    }

    public void setGuideGender(int guideGender) {
        this.guideGender = guideGender;
    }

    public String getServiceCounts() {
        return "服务" + serviceCounts + "次";
    }

    public void setServiceCounts(int serviceCounts) {
        this.serviceCounts = serviceCounts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getGuideStory() {
        return guideStory;
    }

    public void setGuideStory(String guideStory) {
        this.guideStory = guideStory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBuyServices() {
        return buyServices;
    }

    public void setBuyServices(float buyServices) {
        this.buyServices = buyServices;
    }


    public float getGuideScore() {
        return guideScore;
    }

    public void setGuideScore(float guideScore) {
        this.guideScore = guideScore;
    }

    public float getCarConditions() {
        return carConditions;
    }

    public void setCarConditions(float carConditions) {
        this.carConditions = carConditions;
    }

    public int getDriveViable() {
        return driveViable;
    }

    public void setDriveViable(int driveViable) {
        this.driveViable = driveViable;
    }

    public String getGuideAge() {
        return guideAge;
    }

    public void setGuideAge(String guideAge) {
        this.guideAge = guideAge;
    }


    public float getGuideServices() {
        return guideServices;
    }

    public void setGuideServices(float guideServices) {
        this.guideServices = guideServices;
    }

    public int getGuideViable() {
        return guideViable;
    }

    public void setGuideViable(int guideViable) {
        this.guideViable = guideViable;
    }

    public float getTravelArranges() {
        return travelArranges;
    }

    public void setTravelArranges(float travelArranges) {
        this.travelArranges = travelArranges;
    }

    public int getCardViable() {
        return cardViable;
    }

    public void setCardViable(int cardViable) {
        this.cardViable = cardViable;
    }

    public String getServiceAge() {
        return serviceAge;
    }

    public void setServiceAge(String serviceAge) {
        this.serviceAge = serviceAge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobile);
        dest.writeString(guideName);
        dest.writeStringList(language);
        dest.writeStringList(sign);
        dest.writeString(guideImg);
        dest.writeInt(guideGender);
        dest.writeInt(serviceCounts);
        dest.writeInt(count);
        dest.writeString(introduce);
        dest.writeString(guideStory);
        dest.writeString(image);
        dest.writeString(guideAge);
        dest.writeInt(id);
        dest.writeString(serviceAge);
        dest.writeTypedList(travelGuideServiceInfoVOs);
        dest.writeInt(driveViable);
        dest.writeInt(guideViable);
        dest.writeInt(cardViable);
        dest.writeFloat(guideScore);
        dest.writeFloat(travelArranges);
        dest.writeFloat(buyServices);
        dest.writeFloat(carConditions);
        dest.writeFloat(guideServices);
        dest.writeString(shareImg);
        dest.writeString(shareContent);
        dest.writeString(shareTitle);
        dest.writeString(shareUrl);
    }
}
