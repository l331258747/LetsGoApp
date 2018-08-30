package com.njz.letsgoapp.bean.other;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/30
 * Function:
 */

public class ProvinceModel {

    /**
     * travelRegionEntitys : [{"id":36,"parentId":2,"name":"长沙市","spell":"changshashi","initialism":"CS","imgUrl":"xxx","imgLink":"xxx","type":2},{"id":37,"parentId":2,"name":"凤凰","spell":"fenghuang","initialism":"FH","imgUrl":"xxx","imgLink":"xxx","type":2}]
     * proName : 湖南省
     * proId : 2
     */

    private String proName;
    private int proId;
    private List<CityModel> travelRegionEntitys;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public List<CityModel> getTravelRegionEntitys() {
        return travelRegionEntitys;
    }

    public void setTravelRegionEntitys(List<CityModel> travelRegionEntitys) {
        this.travelRegionEntitys = travelRegionEntitys;
    }

}
