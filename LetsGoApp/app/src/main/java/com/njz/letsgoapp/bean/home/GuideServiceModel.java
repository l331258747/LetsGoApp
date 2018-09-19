package com.njz.letsgoapp.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GuideServiceModel implements Parcelable {
    /**
     * guideId : 4
     * commentId : null
     * titleImg : 11111111111
     * servePrice : 111111
     * serveFeature : 11111111
     * serveType : 包车服务
     * renegePriceThree : 111111
     * renegePriceFive : 111111
     * costExplain : 1111111111111
     * title : 11111111111111
     * status : 1
     * location : 北京
     */

    private int id;
    private int serveType;
    private String serviceType;
    private List<ServiceItem> serviceItems;

    public GuideServiceModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ServiceItem> getServiceItems() {
        return serviceItems;
    }

    public void setServiceItems(List<ServiceItem> serviceItems) {
        this.serviceItems = serviceItems;
    }

    public void addServiceItem(ServiceItem serviceItem){
        this.serviceItems.add(serviceItem);
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

    protected GuideServiceModel(Parcel in) {
        id = in.readInt();
        serveType = in.readInt();
        serviceType = in.readString();
        serviceItems = in.createTypedArrayList(ServiceItem.CREATOR);
    }

    public static final Creator<GuideServiceModel> CREATOR = new Creator<GuideServiceModel>() {
        @Override
        public GuideServiceModel createFromParcel(Parcel in) {
            return new GuideServiceModel(in);
        }

        @Override
        public GuideServiceModel[] newArray(int size) {
            return new GuideServiceModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(serveType);
        dest.writeString(serviceType);
        dest.writeTypedList(serviceItems);
    }
}