package com.njz.letsgoapp.bean.home;

import java.util.List;

public class GuideServiceModel {
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
    private String serveType;
    private List<ServiceItem> serviceItems;

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

    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
    }

}