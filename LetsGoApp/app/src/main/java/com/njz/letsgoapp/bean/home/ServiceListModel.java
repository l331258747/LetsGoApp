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
    private int servePrice;
    private String serveType;
    private String title;
    private String location;
    private Object count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServePrice() {
        return servePrice;
    }

    public void setServePrice(int servePrice) {
        this.servePrice = servePrice;
    }

    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
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

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }
}
