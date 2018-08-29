package com.njz.letsgoapp.bean.home;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.njz.letsgoapp.util.GsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

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

    private int guideId;
    private String mobile;
    private Object guideAge;
    private String guideName;
    private Object serviceAge;
    private String language;
    private String sign;
    private Object authViable;
    private Object guideViable;
    private Object driveViable;
    private String guideImg;
    private int guideGender;
    private int serviceCounts;
    private int count;
    private String introduce;
    private double guideScore;
    private Object guideServices;
    private Object carConditions;
    private Object buyServices;
    private Object travelArranges;
    private Object guideStory;
    private CommentModel travelFirstReviewVO;
    private List<GuideServiceModel> travelGuideServiceEntitys;

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getGuideAge() {
        return guideAge;
    }

    public void setGuideAge(Object guideAge) {
        this.guideAge = guideAge;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public Object getServiceAge() {
        return serviceAge;
    }

    public void setServiceAge(Object serviceAge) {
        this.serviceAge = serviceAge;
    }

    public List<String> getLanguage() {

        List<String> languages = new ArrayList<>();
        if(TextUtils.isEmpty(sign)) return languages;

        languages = GsonUtil.convertString2Collection(language,new TypeToken<List<String>>(){});
        return languages;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getSign() {
        List<String> signs = new ArrayList<>();
        if(TextUtils.isEmpty(sign)) return signs;

        signs = GsonUtil.convertString2Collection(sign,new TypeToken<List<String>>(){});
        return signs;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Object getAuthViable() {
        return authViable;
    }

    public void setAuthViable(Object authViable) {
        this.authViable = authViable;
    }

    public Object getGuideViable() {
        return guideViable;
    }

    public void setGuideViable(Object guideViable) {
        this.guideViable = guideViable;
    }

    public Object getDriveViable() {
        return driveViable;
    }

    public void setDriveViable(Object driveViable) {
        this.driveViable = driveViable;
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

    public double getGuideScore() {
        return guideScore;
    }

    public void setGuideScore(double guideScore) {
        this.guideScore = guideScore;
    }

    public Object getGuideServices() {
        return guideServices;
    }

    public void setGuideServices(Object guideServices) {
        this.guideServices = guideServices;
    }

    public Object getCarConditions() {
        return carConditions;
    }

    public void setCarConditions(Object carConditions) {
        this.carConditions = carConditions;
    }

    public Object getBuyServices() {
        return buyServices;
    }

    public void setBuyServices(Object buyServices) {
        this.buyServices = buyServices;
    }

    public Object getTravelArranges() {
        return travelArranges;
    }

    public void setTravelArranges(Object travelArranges) {
        this.travelArranges = travelArranges;
    }

    public Object getGuideStory() {
        return guideStory;
    }

    public void setGuideStory(Object guideStory) {
        this.guideStory = guideStory;
    }

    public CommentModel getTravelFirstReviewVO() {
        return travelFirstReviewVO;
    }

    public void setTravelFirstReviewVO(CommentModel travelFirstReviewVO) {
        this.travelFirstReviewVO = travelFirstReviewVO;
    }

    public List<GuideServiceModel> getTravelGuideServiceEntitys() {
        return travelGuideServiceEntitys;
    }

    public void setTravelGuideServiceEntitys(List<GuideServiceModel> travelGuideServiceEntitys) {
        this.travelGuideServiceEntitys = travelGuideServiceEntitys;
    }
}
