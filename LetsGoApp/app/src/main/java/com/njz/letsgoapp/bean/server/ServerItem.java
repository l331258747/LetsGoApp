package com.njz.letsgoapp.bean.server;

import android.os.Parcel;
import android.os.Parcelable;
import com.njz.letsgoapp.constant.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/13
 * Function:
 */

public class ServerItem implements Parcelable {

    int serveNum;   //数量
    String selectTimeValueList;//时间  2018，2021
    int njzGuideServeId;    //服务id
    String njzGuideServeFormatId;   //规格  1,2
    String titile;//标题
    String img;//图片
    float price;//价格
    String serviceTypeName;//服务名称
    int serverType;
    String location;
    float bugGet;

    protected ServerItem(Parcel in) {
        serveNum = in.readInt();
        selectTimeValueList = in.readString();
        njzGuideServeId = in.readInt();
        njzGuideServeFormatId = in.readString();
        titile = in.readString();
        img = in.readString();
        price = in.readFloat();
        serviceTypeName = in.readString();
        serverType = in.readInt();
        location = in.readString();
        bugGet = in.readFloat();
    }

    public static final Creator<ServerItem> CREATOR = new Creator<ServerItem>() {
        @Override
        public ServerItem createFromParcel(Parcel in) {
            return new ServerItem(in);
        }

        @Override
        public ServerItem[] newArray(int size) {
            return new ServerItem[size];
        }
    };

    public float getBugGet() {
        return bugGet;
    }

    public void setBugGet(float bugGet) {
        this.bugGet = bugGet;
    }

    public ServerItem() {
    }


    public int getServeNum() {
        return serveNum;
    }

    public void setServeNum(int serveNum) {
        this.serveNum = serveNum;
    }

    public String getSelectTimeValueList() {
        return selectTimeValueList;
    }

    public List<String> getSelectTimeValueList2(){
        String[] strs = selectTimeValueList.split(",");
        List<String> lists = new ArrayList<>(Arrays.asList(strs));
        return lists;
    }

    public void setSelectTimeValueList(String selectTimeValueList) {
        this.selectTimeValueList = selectTimeValueList;
    }

    public int getNjzGuideServeId() {
        return njzGuideServeId;
    }

    public void setNjzGuideServeId(int njzGuideServeId) {
        this.njzGuideServeId = njzGuideServeId;
    }

    public String getNjzGuideServeFormatId() {
        return njzGuideServeFormatId;
    }

    public List<String> getNjzGuideServeFormatId2(){
        String[] strs = njzGuideServeFormatId.split(",");
        List<String> lists = new ArrayList<>(Arrays.asList(strs));
        return lists;
    }

    public void setNjzGuideServeFormatId(String njzGuideServeFormatId) {
        this.njzGuideServeFormatId = njzGuideServeFormatId;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getTimeTitle(){
        switch (serverType){
            case Constant.SERVER_TYPE_CUSTOM_ID:
                return "行程时间";
            case Constant.SERVER_TYPE_GUIDE_ID:
                return "行程时间";
            case Constant.SERVER_TYPE_HOTEL_ID:
                return "入住时间";
            case Constant.SERVER_TYPE_TICKET_ID:
                return "日期";
            case Constant.SERVER_TYPE_CAR_ID:
                return "出发日期";
            case Constant.SERVER_TYPE_FEATURE_ID:
                return "出发日期";
        }
        return "";
    }

    public String getCountContent(){
        switch (serverType){
            case Constant.SERVER_TYPE_CUSTOM_ID:
                return serveNum + "";
            case Constant.SERVER_TYPE_GUIDE_ID:
                return serveNum + "";
            case Constant.SERVER_TYPE_HOTEL_ID:
                return serveNum + "间";
            case Constant.SERVER_TYPE_TICKET_ID:
                return serveNum + "张";
            case Constant.SERVER_TYPE_CAR_ID:
                return serveNum + "次";
            case Constant.SERVER_TYPE_FEATURE_ID:
                return serveNum + "次";
        }
        return "";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(serveNum);
        dest.writeString(selectTimeValueList);
        dest.writeInt(njzGuideServeId);
        dest.writeString(njzGuideServeFormatId);
        dest.writeString(titile);
        dest.writeString(img);
        dest.writeFloat(price);
        dest.writeString(serviceTypeName);
        dest.writeInt(serverType);
        dest.writeString(location);
        dest.writeFloat(bugGet);
    }
}
