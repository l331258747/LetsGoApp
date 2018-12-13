package com.njz.letsgoapp.bean.server;

import android.os.Parcel;
import android.os.Parcelable;

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

    public ServerItem() {
    }

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

    public int getServeNum() {
        return serveNum;
    }

    public void setServeNum(int serveNum) {
        this.serveNum = serveNum;
    }

    public String getSelectTimeValueList() {
        return selectTimeValueList;
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

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
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
    }
}
