package com.njz.letsgoapp.bean.server;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/13
 * Function:
 */

public class SubmitOrderModel {

    int guideId;
    String mobile;
    String name;
    int personNum;
    String specialRequire;
    String location;
    SubmitOrderChildModel njzGuideServeToOrderDto;
    int children;
    int adult;
    int bugGet;

    public void setChildren(int children) {
        this.children = children;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public void setBugGet(int bugGet) {
        this.bugGet = bugGet;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }


    public void setSpecialRequire(String specialRequire) {
        this.specialRequire = specialRequire;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public void setNjzGuideServeToOrderDto(SubmitOrderChildModel njzGuideServeToOrderDto) {
        this.njzGuideServeToOrderDto = njzGuideServeToOrderDto;
    }

    public String getLocation() {
        return location;
    }
}
