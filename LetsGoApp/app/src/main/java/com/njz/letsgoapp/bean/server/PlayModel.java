package com.njz.letsgoapp.bean.server;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/3
 * Function:
 */

public class PlayModel {


    /**
     * titleImg : xxxx
     * address : 长沙
     * servePrice : 100.0
     * gender : 0
     * serveType : 1
     * guideId : 1
     * guideImg : http://pc03h8bbw.bkt.clouddn.com/1/20180731/1639442522501.jpg
     * serveMinNum : 1
     * title : 长沙美女带你游 | 带车车
     * score : 0
     * njzGuideServeFormatEntitys : [{"njzGuideServeFormatDic":"xdpy_cx","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":1,"guideServeFormatName":"车辆类型0","id":1},{"njzGuideServeFormatDic":"xdpy_cx","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":1,"guideServeFormatName":"车辆类型1","id":2},{"njzGuideServeFormatDic":"xdpy_cx","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":1,"guideServeFormatName":"车辆类型2","id":3},{"njzGuideServeFormatDic":"xdpy_cx","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":1,"guideServeFormatName":"车辆类型3","id":4},{"njzGuideServeFormatDic":"xdpy_yy","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":1,"guideServeFormatName":"语言类型4","id":5},{"njzGuideServeFormatDic":"xdpy_yy","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":1,"guideServeFormatName":"语言类型5","id":6},{"njzGuideServeFormatDic":"xdpy_yy","servePriceSelect":"","serveDefaultPrice":100,"formatUnit":"","njzGuideServeId":1,"guideServeFormatName":"语言类型6","id":7}]
     * serveMaxNum : 1
     * reviewCount : 0
     * sellCount : 0
     * commentId : 0
     * id : 1
     * serveTypeName : xdpy
     * status : 0
     */

    private String titleImg;
    private String address;
    private float servePrice;
    private int gender;
    private int serveType;
    private int guideId;
    private String guideImg;
    private int serveMinNum;
    private String title;
    private int score;
    private int serveMaxNum;
    private int reviewCount;
    private int sellCount;
    private int commentId;
    private int id;
    private String serveTypeName;
    private int status;
    private List<PlayChileMedel> njzGuideServeFormatEntitys;


    public String getTitleImg() {
        return titleImg;
    }

    public String getAddress() {
        return address;
    }

    public float getServePrice() {
        return servePrice;
    }

    public int getGender() {
        return gender;
    }

    public int getServeType() {
        return serveType;
    }

    public int getGuideId() {
        return guideId;
    }

    public String getGuideImg() {
        return guideImg;
    }

    public int getServeMinNum() {
        return serveMinNum;
    }

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return score;
    }

    public int getServeMaxNum() {
        return serveMaxNum;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int getSellCount() {
        return sellCount;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getId() {
        return id;
    }

    public String getServeTypeName() {
        return serveTypeName;
    }

    public int getStatus() {
        return status;
    }

    public List<PlayChileMedel> getNjzGuideServeFormatEntitys() {
        return njzGuideServeFormatEntitys;
    }
}
