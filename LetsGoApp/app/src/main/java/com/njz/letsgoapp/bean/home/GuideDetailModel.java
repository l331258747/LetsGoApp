package com.njz.letsgoapp.bean.home;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/29
 * Function:
 */

public class GuideDetailModel {


    /**
     * guideId : 4
     * mobile : 1234511
     * guideAge : null
     * guideName : liyu
     * serviceAge : null
     * language : ["中文",“英文”]
     * sign : ["熟悉当地","会攀岩","喜欢自驾"]
     * authViable : null
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
     * authViable : 0
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
    private int buyServices;
    private double guideScore;
    private String image;
    private int carConditions;
    private int driveViable;
    private String guideAge;
    private double guideServices;
    private int guideViable;
    private EvaluateModel travelFirstReviewVO;
    private int travelArranges;
    private int authViable;
    private int id;
    private String serviceAge;
    private List<GuideServiceModel> travelGuideServiceInfoVOs;

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

    public EvaluateModel getTravelFirstReviewVO() {
        return travelFirstReviewVO;
    }

    public void setTravelFirstReviewVO(EvaluateModel travelFirstReviewVO) {
        this.travelFirstReviewVO = travelFirstReviewVO;
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

    public int getBuyServices() {
        return buyServices;
    }

    public void setBuyServices(int buyServices) {
        this.buyServices = buyServices;
    }


    public double getGuideScore() {
        return guideScore;
    }

    public void setGuideScore(double guideScore) {
        this.guideScore = guideScore;
    }

    public int getCarConditions() {
        return carConditions;
    }

    public void setCarConditions(int carConditions) {
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


    public double getGuideServices() {
        return guideServices;
    }

    public void setGuideServices(double guideServices) {
        this.guideServices = guideServices;
    }

    public int getGuideViable() {
        return guideViable;
    }

    public void setGuideViable(int guideViable) {
        this.guideViable = guideViable;
    }

    public int getTravelArranges() {
        return travelArranges;
    }

    public void setTravelArranges(int travelArranges) {
        this.travelArranges = travelArranges;
    }

    public int getAuthViable() {
        return authViable;
    }

    public void setAuthViable(int authViable) {
        this.authViable = authViable;
    }

    public String getServiceAge() {
        return serviceAge;
    }

    public void setServiceAge(String serviceAge) {
        this.serviceAge = serviceAge;
    }
}
