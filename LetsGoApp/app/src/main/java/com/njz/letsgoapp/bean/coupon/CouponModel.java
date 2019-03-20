package com.njz.letsgoapp.bean.coupon;

import com.njz.letsgoapp.util.StringUtils;

/**
 * Created by LGQ
 * Time: 2019/2/21
 * Function:
 */

public class CouponModel {


    /**
     * fillMoney : 100
     * eventId : 27
     * expireDays : 1
     * instructions : 满减券满100减3
     * isSuperposition : 1
     * useEndDate : 2019-02-28 14:09:18
     * couponId : 42
     * title : 测试3
     * typeMoney : 3
     * couponType : 0
     * expireStatus : 0
     * id : 12
     * couponFlag : 0
     * useEndDateStr : 2019-02-28
     */

    private float fillMoney;
    private int eventId;
    private int expireDays;
    private String instructions;
    private int isSuperposition;
    private String useEndDate;
    private int couponId;
    private String title;
    private float typeMoney;
    private int couponType;
    private int expireStatus;
    private int id;
    private int couponFlag;
    private String useEndDateStr;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public float getFillMoney() {
        return fillMoney;
    }

    public String getFillMoneyStr(){
        return StringUtils.getStringNum(fillMoney);
    }

    public void setFillMoney(float fillMoney) {
        this.fillMoney = fillMoney;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(int expireDays) {
        this.expireDays = expireDays;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getIsSuperposition() {
        return isSuperposition;
    }

    public void setIsSuperposition(int isSuperposition) {
        this.isSuperposition = isSuperposition;
    }

    public String getUseEndDate() {
        return useEndDate;
    }

    public void setUseEndDate(String useEndDate) {
        this.useEndDate = useEndDate;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getTypeMoney() {
        return typeMoney;
    }

    public String getTypeMoneyStr(){
        return StringUtils.getStringNum(typeMoney);
    }

    public void setTypeMoney(float typeMoney) {
        this.typeMoney = typeMoney;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getExpireStatus() {
        return expireStatus;
    }

    public void setExpireStatus(int expireStatus) {
        this.expireStatus = expireStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCouponFlag() {
        return couponFlag;
    }

    public void setCouponFlag(int couponFlag) {
        this.couponFlag = couponFlag;
    }

    public String getUseEndDateStr() {
        return useEndDateStr;
    }

    public void setUseEndDateStr(String useEndDateStr) {
        this.useEndDateStr = useEndDateStr;
    }
}
