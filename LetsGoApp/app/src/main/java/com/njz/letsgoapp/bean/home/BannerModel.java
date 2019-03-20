package com.njz.letsgoapp.bean.home;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public class BannerModel {

    /**
     * id : 1
     * imgName : 图1
     * imgUrl : xxx
     * toUrl : XXX
     * type : 1
     * guideId : null
     */

    private int id;
    private String imgName;
    private String imgUrl;
    private String toUrl;
    private int type;
    private Object guideId;
    private int linkType;
    private int params;

    public int getParams() {
        return params;
    }

    public int getLinkType() {
        return linkType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getToUrl() {
        return toUrl;
    }

    public void setToUrl(String toUrl) {
        this.toUrl = toUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getGuideId() {
        return guideId;
    }

    public void setGuideId(Object guideId) {
        this.guideId = guideId;
    }
}
