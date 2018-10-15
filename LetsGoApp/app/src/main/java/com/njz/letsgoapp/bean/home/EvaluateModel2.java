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
    private float guideService;
    private float carCondition;
    private float buyService;
    private float travelArrange;
    private float score;
    private String userDate;
    private String userContent;
    private String imgUrl;
    private String level;
    private String guideContent;
    private String imageUrls;
    private String guideDate;
    private List<EvaluateServicesModel> services;

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
        return Arrays.asList(strs);
    }


    //导游服务,车辆状况,代订服务,行程安排


    public float getGuideService() {
        return guideService;
    }
    public String getGuideServiceStr(){
        return "导游服务" + guideService;
    }

    public void setGuideService(int guideService) {
        this.guideService = guideService;
    }

    public float getCarCondition() {
        return carCondition;
    }
    public String getCarConditionStr() {
        return "车辆状况" + carCondition;
    }

    public void setCarCondition(int carCondition) {
        this.carCondition = carCondition;
    }

    public float getBuyService() {
        return buyService;
    }
    public String getBuyServiceStr() {
        return "代订服务" + buyService;
    }

    public void setBuyService(int buyService) {
        this.buyService = buyService;
    }

    public float getTravelArrange() {
        return travelArrange;
    }
    public String getTravelArrangeStr() {
        return "行程安排" + travelArrange;
    }

    public void setTravelArrange(int travelArrange) {
        this.travelArrange = travelArrange;
    }

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
}
