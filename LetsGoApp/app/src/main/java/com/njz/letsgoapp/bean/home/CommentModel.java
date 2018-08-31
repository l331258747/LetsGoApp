package com.njz.letsgoapp.bean.home;

import com.njz.letsgoapp.util.DateUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/29
 * Function:
 */

public class CommentModel {

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

    private String name;
    private String guideService;
    private String carCondition;
    private String buyService;
    private String travelArrange;
    private String score;
    private String userDate;
    private String userContent;
    private String img;
    private String level;
    private List<String> imageUrls;

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuideService() {
        return "导游服务"+guideService;
    }

    public void setGuideService(String guideService) {
        this.guideService = guideService;
    }

    public String getCarCondition() {
        return "车辆状况"+carCondition;
    }

    public void setCarCondition(String carCondition) {
        this.carCondition = carCondition;
    }

    public String getBuyService() {
        return "代订服务"+buyService;
    }

    public void setBuyService(String buyService) {
        this.buyService = buyService;
    }

    public String getTravelArrange() {
        return "行程安排"+travelArrange;
    }

    public void setTravelArrange(String travelArrange) {
        this.travelArrange = travelArrange;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUserDate() {
        return DateUtil.longToStr(userDate);
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
