package com.njz.letsgoapp.bean.home;

import java.util.ArrayList;
import java.util.List;

public class GuideModel {
    /**
     * guideId : 4
     * guideName : liyu
     * guideImg : http://pc03h8bbw.bkt.clouddn.com/1/20180731/1639442522501.jpg
     * guideGender : 1
     * serviceCounts : 1
     * count : null
     * introduce : 个人简介
     * guideScore : 0.0
     * travelGuideServiceEntitys : [{"guideId":4,"commentId":null,"titleImg":"11111111111","servePrice":111111,"serveFeature":"11111111","serveType":"包车服务","renegePriceThree":111111,"renegePriceFive":111111,"costExplain":"1111111111111","title":"11111111111111","status":1,"location":"北京"},{"guideId":4,"commentId":null,"titleImg":"11","servePrice":111,"serveFeature":null,"serveType":"私人订制","renegePriceThree":11,"renegePriceFive":11,"costExplain":"11","title":"11","status":1,"location":"张家界"}]
     */

    private int guideId;
    private String guideName;
    private String guideImg;
    private int guideGender;
    private int serviceCounts;
    private int count;
    private String introduce;
    private float guideScore;
    private List<GuideServiceModel> travelGuideServiceInfoVOs;
    /**
     * guideScore : 0
     * lcoation :
     * travelGuideServiceInfoEntitys : null
     * id : 1
     */

    private int id;


    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
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

    public int getServiceCounts() {
        return serviceCounts;
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

    public float getGuideScore() {
        return guideScore;
    }

    public void setGuideScore(float guideScore) {
        this.guideScore = guideScore;
    }

    public List<GuideServiceModel> getTravelGuideServiceInfoVOs() {
        return travelGuideServiceInfoVOs;
    }

    public void setTravelGuideServiceInfoEntitys(List<GuideServiceModel> travelGuideServiceInfoVOs) {
        this.travelGuideServiceInfoVOs = travelGuideServiceInfoVOs;
    }

    public List<String> getServiceTags(){
        List<String> serviceTags= new ArrayList<>();
        if(travelGuideServiceInfoVOs == null) return serviceTags;
        for (int i = 0;i<travelGuideServiceInfoVOs.size();i++){
            serviceTags.add(travelGuideServiceInfoVOs.get(i).getServiceType());
        }
        return serviceTags;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}