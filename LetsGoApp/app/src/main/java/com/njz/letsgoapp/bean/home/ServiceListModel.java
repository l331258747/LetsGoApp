package com.njz.letsgoapp.bean.home;

/**
 * Created by LGQ
 * Time: 2018/8/31
 * Function:
 */

public class ServiceListModel {

    /**
     * id : 34
     * servePrice : 111111
     * serveType : 代订门票
     * title : 张家界天门山门票
     * location : 张家界
     * count : null
     */

    private int id;
    private float servePrice;
    private int serveType;
    private String serviceType;
    private String title;
    private String location;
    private String count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getServePrice() {
        return servePrice;
    }

    public void setServePrice(int servePrice) {
        this.servePrice = servePrice;
    }


    public void setServePrice(float servePrice) {
        this.servePrice = servePrice;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
