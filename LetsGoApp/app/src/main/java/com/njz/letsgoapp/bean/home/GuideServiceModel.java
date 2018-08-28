package com.njz.letsgoapp.bean.home;

public class GuideServiceModel {
    /**
     * guideId : 4
     * commentId : null
     * titleImg : 11111111111
     * servePrice : 111111
     * serveFeature : 11111111
     * serveType : 包车服务
     * renegePriceThree : 111111
     * renegePriceFive : 111111
     * costExplain : 1111111111111
     * title : 11111111111111
     * status : 1
     * location : 北京
     */

    private int guideId;
    private Object commentId;
    private String titleImg;
    private int servePrice;
    private String serveFeature;
    private String serveType;
    private int renegePriceThree;
    private int renegePriceFive;
    private String costExplain;
    private String title;
    private int status;
    private String location;

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public Object getCommentId() {
        return commentId;
    }

    public void setCommentId(Object commentId) {
        this.commentId = commentId;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public int getServePrice() {
        return servePrice;
    }

    public void setServePrice(int servePrice) {
        this.servePrice = servePrice;
    }

    public String getServeFeature() {
        return serveFeature;
    }

    public void setServeFeature(String serveFeature) {
        this.serveFeature = serveFeature;
    }

    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
    }

    public int getRenegePriceThree() {
        return renegePriceThree;
    }

    public void setRenegePriceThree(int renegePriceThree) {
        this.renegePriceThree = renegePriceThree;
    }

    public int getRenegePriceFive() {
        return renegePriceFive;
    }

    public void setRenegePriceFive(int renegePriceFive) {
        this.renegePriceFive = renegePriceFive;
    }

    public String getCostExplain() {
        return costExplain;
    }

    public void setCostExplain(String costExplain) {
        this.costExplain = costExplain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}