package com.njz.letsgoapp.bean.home;

import android.text.TextUtils;

import com.njz.letsgoapp.util.GsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/29
 * Function:
 */

public class EvaluateModel2 {

    /**
     * name : null
     * guideService : null
     * carCondition : null
     * buyService : null
     * travelArrange : null
     * score : null
     * userDate : null
     * userContent : null
     * img : null
     * level : null
     */

    /*
    {
            "imgUrl":"http://pc03h8bbw.bkt.clouddn.com/1/20180912/100134155fc738.jpg",
            "buyService":5,
            "score":4.8,
            "userLevel":44,
            "guideService":4,
            "imageUrls":[
                "A",
                "B",
                "C"
            ],
            "userContent":"还不错，价格实惠",
            "nickname":"挺有用沫",
            "carCondition":5,
            "travelArrange":5,
            "userDate":"2018-08-15 11:18:44"
        }
     */

    private String nickname;

    private float serviceAttitude;
    private float serviceQuality;
    private float travelArrange;
    private float carCondition;

    private float score;
    private String userDate;
    private String userContent;
    private String imgUrl;
    private String level;
    private String guideContent;
    private String imageUrls;
    private String guideDate;
    private boolean isCustom;
    private List<EvaluateServicesModel> services;

    public boolean isCustom() {
        return isCustom;
    }

    public List<EvaluateServicesModel> getServices() {
        return services;
    }

    public String getServicesStr(){
        if(services == null || services.size() == 0)
            return "";

        StringBuffer sb = new StringBuffer();
        for (EvaluateServicesModel data:services){
            sb.append(data.getTitle()+"\n");
        }
        return sb.substring(0,sb.length() - 1);
    }

    public String getGuideDate() {
        return guideDate;
    }

    public String getGuideContent() {
        return guideContent;
    }

    public List<String> getImageUrls() {
        if(TextUtils.isEmpty(imageUrls)){
            return new ArrayList<>();
        }

        String[] strs = imageUrls.split(",");
        List<String> lists = new ArrayList<>(Arrays.asList(strs));
        return lists;
    }


    //导游服务,车辆状况,代订服务,行程安排
    //服务态度，服务质量，行程安排，车辆状况，方案定制，行程体验


    public String getScore() {
        return ""+score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setGuideContent(String guideContent) {
        this.guideContent = guideContent;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setGuideDate(String guideDate) {
        this.guideDate = guideDate;
    }

    public void setServices(List<EvaluateServicesModel> services) {
        this.services = services;
    }

    public float getServiceAttitude() {
        return serviceAttitude;
    }

    public float getServiceQuality() {
        return serviceQuality;
    }

    public float getTravelArrange() {
        return travelArrange;
    }

    public float getCarCondition() {
        return carCondition;
    }

    public String getServiceAttitudeStr() {
        return "服务态度"+serviceAttitude;
    }
    public String getServiceQualityStr() {
        if(isCustom)
            return "方案设计"+serviceQuality;
        return "服务质量"+serviceQuality;
    }
    public String  getTravelArrangeStr() {
        if(isCustom)
            return "行程体验"+serviceQuality;
        return "行程安排"+travelArrange;
    }

    public String getCarConditionStr() {
        return "车辆状况"+carCondition;
    }
}
