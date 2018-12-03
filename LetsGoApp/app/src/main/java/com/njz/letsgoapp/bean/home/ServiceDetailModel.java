package com.njz.letsgoapp.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LGQ
 * Time: 2018/8/31
 * Function:
 */

public class ServiceDetailModel implements Parcelable{


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
    private int commentId;
    private String titleImg;
    private float servePrice;
    private String serveFeature;
    private int serveType;
    private String serviceType;
    private String value;
    private String renegePriceThree;
    private String renegePriceFive;
    private String costExplain;
    private String title;
    private int status;
    private String location;
    private int sellOut;
    private String guideMobile;

    private String shareImg;
    private String shareContent;
    private String shareTitle;
    private String shareUrl;
    private String address;

    protected ServiceDetailModel(Parcel in) {
        id = in.readInt();
        guideId = in.readInt();
        commentId = in.readInt();
        titleImg = in.readString();
        servePrice = in.readFloat();
        serveFeature = in.readString();
        serveType = in.readInt();
        serviceType = in.readString();
        value = in.readString();
        renegePriceThree = in.readString();
        renegePriceFive = in.readString();
        costExplain = in.readString();
        title = in.readString();
        status = in.readInt();
        location = in.readString();
        sellOut = in.readInt();
        guideMobile = in.readString();
        shareImg = in.readString();
        shareContent = in.readString();
        shareTitle = in.readString();
        shareUrl = in.readString();
        address = in.readString();
    }

    public static final Creator<ServiceDetailModel> CREATOR = new Creator<ServiceDetailModel>() {
        @Override
        public ServiceDetailModel createFromParcel(Parcel in) {
            return new ServiceDetailModel(in);
        }

        @Override
        public ServiceDetailModel[] newArray(int size) {
            return new ServiceDetailModel[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public String getShareImg() {
        return shareImg;
    }

    public String getShareContent() {
        return shareContent;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public String getGuideMobile() {
        return guideMobile;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
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

    public int getServeType() {
        return serveType;
    }

    public void setServeType(int serveType) {
        this.serveType = serveType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRenegePriceThree() {
        return renegePriceThree;
    }

    public void setRenegePriceThree(String renegePriceThree) {
        this.renegePriceThree = renegePriceThree;
    }

    public String getRenegePriceFive() {
        return renegePriceFive;
    }

    public void setRenegePriceFive(String renegePriceFive) {
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

    public int getCount() {
        return sellOut;
    }

    public void setCount(int count) {
        this.sellOut = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(guideId);
        dest.writeInt(commentId);
        dest.writeString(titleImg);
        dest.writeFloat(servePrice);
        dest.writeString(serveFeature);
        dest.writeInt(serveType);
        dest.writeString(serviceType);
        dest.writeString(value);
        dest.writeString(renegePriceThree);
        dest.writeString(renegePriceFive);
        dest.writeString(costExplain);
        dest.writeString(title);
        dest.writeInt(status);
        dest.writeString(location);
        dest.writeInt(sellOut);
        dest.writeString(guideMobile);
        dest.writeString(shareImg);
        dest.writeString(shareContent);
        dest.writeString(shareTitle);
        dest.writeString(shareUrl);
        dest.writeString(address);
    }
}
