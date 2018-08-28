package com.njz.letsgoapp.bean.home;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public class GuideModel {

    /**
     * guideId : 1
     * guideName : lm
     * guideImg : http://pc03h8bbw.bkt.clouddn.com/1/20180731/1639442522501.jpg
     * guideGender : 0
     * serviceCounts : 3
     * count : 3
     * introduce : 美丽
     * guideScore : 0
     */

    private int guideId;
    private String guideName;
    private String guideImg;
    private int guideGender;
    private int serviceCounts;
    private int count;
    private String introduce;
    private int guideScore;

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

    public int getGuideScore() {
        return guideScore;
    }

    public void setGuideScore(int guideScore) {
        this.guideScore = guideScore;
    }
}
