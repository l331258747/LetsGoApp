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

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public String getSpecialRequire() {
        return specialRequire;
    }

    public void setSpecialRequire(String specialRequire) {
        this.specialRequire = specialRequire;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public SubmitOrderChildModel getNjzGuideServeToOrderDto() {
        return njzGuideServeToOrderDto;
    }

    public void setNjzGuideServeToOrderDto(SubmitOrderChildModel njzGuideServeToOrderDto) {
        this.njzGuideServeToOrderDto = njzGuideServeToOrderDto;
    }

}
