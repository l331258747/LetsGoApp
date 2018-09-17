package com.njz.letsgoapp.bean.home;

/**
 * Created by LGQ
 * Time: 2018/8/31
 * Function:
 */

public class ServiceDetailModel {


    /**
     * id : 14
     * guideId : 5
     * commentId : null
     * titleImg : 11111111111
     * servePrice : 111111
     * serveFeature : 11111111
     * serveType : 车导服务
     * renegePriceThree : 111111
     * renegePriceFive : 111111
     * costExplain : 1111111111111
     * title : 奔驰大队带你飞
     * status : 1
     * location : 张家界
     * count : null
     */

    private int id;
    private int guideId;
    private Object commentId;
    private String titleImg;
    private float servePrice;
    private String serveFeature;
    private String serveType;
    private int renegePriceThree;
    private int renegePriceFive;
    private String costExplain;
    private String title;
    private int status;
    private String location;
    private Object count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public float getServePrice() {
        return servePrice;
    }

    public void setServePrice(float servePrice) {
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

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }
}
