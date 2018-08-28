package com.njz.letsgoapp.bean.home;

import java.util.List;

public class DynamicModel {

    /**
     * imgUrl : null
     * nickname : null
     * userLevel : 0
     * content : 无敌宇哥热！
     * startTime : 2周前
     * lon : 0
     * lat : 0
     * location : 长沙
     * imgUrls : ["https://goss4.vcg.com/creative/vcg/800/new/gic13822671.jpg","https://goss4.vcg.com/creative/vcg/800/version23/VCG41482576946.jpg","https://goss.vcg.com/creative/vcg/800/version23/VCG21gic13799690.jpg"]
     * friendSterId : 10
     * replyCount : 0
     * likeCount : 0
     * gender : 0
     * travelDiscussEntity : null
     * userId : 1
     */

    private String imgUrl;
    private String nickname;
    private int userLevel;
    private String content;
    private String startTime;
    private int lon;
    private int lat;
    private String location;
    private int friendSterId;
    private int replyCount;
    private int likeCount;
    private int gender;
    private Object travelDiscussEntity;
    private int userId;
    private List<String> imgUrls;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFriendSterId() {
        return friendSterId;
    }

    public void setFriendSterId(int friendSterId) {
        this.friendSterId = friendSterId;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Object getTravelDiscussEntity() {
        return travelDiscussEntity;
    }

    public void setTravelDiscussEntity(Object travelDiscussEntity) {
        this.travelDiscussEntity = travelDiscussEntity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}