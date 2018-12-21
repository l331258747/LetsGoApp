package com.njz.letsgoapp.bean.server;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by LGQ
 * Time: 2018/12/21
 * Function:
 */

public class CustomPlanModel implements Parcelable{


    /**
     * njzGuideServeFormatDefaultJson : []
     * titleImg : xxxx
     * servePrice : 0
     * travelDesign :
     * orderId : 92
     * personNumJson : {"children":0,"adult":1}
     * serveNum : 1
     * serveType : 3
     * guideId : 1
     * njzGuideServeId : 8
     * title : 长沙噢先生1人私人订制游
     * renegePriceThree : 7,4
     * offerDetail :
     * doPlanStatus : 2
     * children : 0
     * travelDate : 2018-12-27,2018-12-28,2018-12-29,2018-12-30,2018-12-31
     * bugGet : 600.0
     * id : 31
     * adult : 1
     * value : srdz
     * renegePriceFive : 3,1
     */

    private String njzGuideServeFormatDefaultJson;
    private String titleImg;
    private float servePrice;
    private String travelDesign;
    private int orderId;
    private String personNumJson;
    private int serveNum;
    private int serveType;
    private int guideId;
    private int njzGuideServeId;
    private String title;
    private String renegePriceThree;
    private String offerDetail;
    private int doPlanStatus;
    private int children;
    private String travelDate;
    private float bugGet;
    private int id;
    private int adult;
    private String value;
    private String renegePriceFive;

    protected CustomPlanModel(Parcel in) {
        njzGuideServeFormatDefaultJson = in.readString();
        titleImg = in.readString();
        servePrice = in.readFloat();
        travelDesign = in.readString();
        orderId = in.readInt();
        personNumJson = in.readString();
        serveNum = in.readInt();
        serveType = in.readInt();
        guideId = in.readInt();
        njzGuideServeId = in.readInt();
        title = in.readString();
        renegePriceThree = in.readString();
        offerDetail = in.readString();
        doPlanStatus = in.readInt();
        children = in.readInt();
        travelDate = in.readString();
        bugGet = in.readFloat();
        id = in.readInt();
        adult = in.readInt();
        value = in.readString();
        renegePriceFive = in.readString();
    }

    public static final Creator<CustomPlanModel> CREATOR = new Creator<CustomPlanModel>() {
        @Override
        public CustomPlanModel createFromParcel(Parcel in) {
            return new CustomPlanModel(in);
        }

        @Override
        public CustomPlanModel[] newArray(int size) {
            return new CustomPlanModel[size];
        }
    };

    public String getNjzGuideServeFormatDefaultJson() {
        return njzGuideServeFormatDefaultJson;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public float getServePrice() {
        return servePrice;
    }

    public String getTravelDesign() {
        return travelDesign;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getPersonNumJson() {
        return personNumJson;
    }

    public int getServeNum() {
        return serveNum;
    }

    public int getServeType() {
        return serveType;
    }

    public int getGuideId() {
        return guideId;
    }

    public int getNjzGuideServeId() {
        return njzGuideServeId;
    }

    public String getTitle() {
        return title;
    }

    public String getRenegePriceThree() {
        return renegePriceThree;
    }

    public String getOfferDetail() {
        return offerDetail;
    }

    public int getDoPlanStatus() {
        return doPlanStatus;
    }

    public int getChildren() {
        return children;
    }

    //2018-12-27,2018-12-28,2018-12-29,2018-12-30,2018-12-31
    public String getTravelDate() {
        if(TextUtils.isEmpty(travelDate)){
            return "0天";
        }
        String[] dates = travelDate.split(",");
        return dates[0] + " 共" + dates.length + "天 " + dates[dates.length - 1];
    }

    public float getBugGet() {
        return bugGet;
    }

    public int getId() {
        return id;
    }

    public int getAdult() {
        return adult;
    }

    public String getPersonNum(){
        return adult+"成人"+children+"儿童";
    }

    public String getValue() {
        return value;
    }

    public String getRenegePriceFive() {
        return renegePriceFive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(njzGuideServeFormatDefaultJson);
        dest.writeString(titleImg);
        dest.writeFloat(servePrice);
        dest.writeString(travelDesign);
        dest.writeInt(orderId);
        dest.writeString(personNumJson);
        dest.writeInt(serveNum);
        dest.writeInt(serveType);
        dest.writeInt(guideId);
        dest.writeInt(njzGuideServeId);
        dest.writeString(title);
        dest.writeString(renegePriceThree);
        dest.writeString(offerDetail);
        dest.writeInt(doPlanStatus);
        dest.writeInt(children);
        dest.writeString(travelDate);
        dest.writeFloat(bugGet);
        dest.writeInt(id);
        dest.writeInt(adult);
        dest.writeString(value);
        dest.writeString(renegePriceFive);
    }
}
