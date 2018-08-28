package com.njz.letsgoapp.bean.home;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public class GuideListModel {

    /**
     * totalCount : 2
     * pageSize : 10
     * totalPage : 1
     * currPage : 1
     * list : [{"guideId":4,"guideName":"liyu","guideImg":"http://pc03h8bbw.bkt.clouddn.com/1/20180731/1639442522501.jpg","guideGender":1,"serviceCounts":1,"count":null,"introduce":"个人简介","guideScore":0,"travelGuideServiceEntitys":[{"guideId":4,"commentId":null,"titleImg":"11111111111","servePrice":111111,"serveFeature":"11111111","serveType":"包车服务","renegePriceThree":111111,"renegePriceFive":111111,"costExplain":"1111111111111","title":"11111111111111","status":1,"location":"北京"},{"guideId":4,"commentId":null,"titleImg":"11","servePrice":111,"serveFeature":null,"serveType":"私人订制","renegePriceThree":11,"renegePriceFive":11,"costExplain":"11","title":"11","status":1,"location":"张家界"}]},{"guideId":5,"guideName":"sj","guideImg":"http://pc03h8bbw.bkt.clouddn.com/1/20180731/1639442522501.jpg","guideGender":1,"serviceCounts":0,"count":null,"introduce":"牛逼","guideScore":0,"travelGuideServiceEntitys":[{"guideId":5,"commentId":null,"titleImg":"11111111111","servePrice":111111,"serveFeature":"11111111","serveType":"包车服务","renegePriceThree":111111,"renegePriceFive":111111,"costExplain":"1111111111111","title":"11111111111111","status":1,"location":"张家界"},{"guideId":5,"commentId":null,"titleImg":"11111111111","servePrice":111111,"serveFeature":"11111111","serveType":"代订酒店","renegePriceThree":111111,"renegePriceFive":111111,"costExplain":"1111111111111","title":"11111111111111","status":1,"location":"张家界"},{"guideId":5,"commentId":null,"titleImg":"11111111111","servePrice":111111,"serveFeature":"11111111","serveType":"代订门票","renegePriceThree":111111,"renegePriceFive":111111,"costExplain":"1111111111111","title":"11111111111111","status":1,"location":"张家界"}]}]
     */

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<GuideModel> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<GuideModel> getList() {
        return list;
    }

    public void setList(List<GuideModel> list) {
        this.list = list;
    }


}
