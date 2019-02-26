package com.njz.letsgoapp.bean.coupon;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/26
 * Function:
 */

public class CouponReceiveModel {

    /**
     * image : https://najiuzou-1256768961.file.myqcloud.com/upload/20190215/1140097756db2f.png
     * popoutImage : https://najiuzou-1256768961.file.myqcloud.com/upload/20190221/10035174743448.png
     * startDateStr : 2019年02月14日
     * endDate : 2019-02-27 00:00:00
     * onlineStatus : 1
     * remindScheme : 1
     * rule : 123
     * eventCouponTotalPrice : 6
     * title : 测试1
     * operatorName :
     * remindTime : 2019-02-21 10:03:53
     * background : #FFEC8B
     * couponList : [{"fillMoney":100,"eventId":27,"expireDays":1,"instructions":"使用说明使用说明12","isValidNumber":0,"useStartDateStr":"2019-02-22","eventList":null,"isSuperposition":1,"useEndDate":"2019-02-23 00:00:00","title":"测试1","userId":0,"useStartDate":"2019-02-22 00:00:00","limitGet":0,"number":0,"typeMoney":1,"couponType":"0","sendType":"","sendStatus":1,"id":40,"useEndDateStr":"2019-02-23","validDays":0},{"fillMoney":100,"eventId":27,"expireDays":1,"instructions":"满减券满100减2","isValidNumber":0,"useStartDateStr":"","eventList":null,"isSuperposition":1,"useEndDate":null,"title":"测试2","userId":0,"useStartDate":null,"limitGet":0,"number":0,"typeMoney":2,"couponType":"0","sendType":"","sendStatus":1,"id":41,"useEndDateStr":"领取后，3天内有效","validDays":3},{"fillMoney":100,"eventId":27,"expireDays":1,"instructions":"满减券满100减3","isValidNumber":0,"useStartDateStr":"","eventList":null,"isSuperposition":1,"useEndDate":null,"title":"测试3","userId":0,"useStartDate":null,"limitGet":0,"number":0,"typeMoney":3,"couponType":"0","sendType":"","sendStatus":1,"id":42,"useEndDateStr":"领取后，1天内有效","validDays":1}]
     * eventStatus : 1
     * isRemind : 1
     * isShare : 0
     * id : 27
     * operatorId : 1
     * endDateStr : 2019年02月27日
     * startDate : 2019-02-14 00:00:00
     * createDate : 2019-02-15 11:51:42
     */

    private String image;
    private String popoutImage;
    private String startDateStr;
    private String endDate;
    private int onlineStatus;
    private int remindScheme;
    private String rule;
    private int eventCouponTotalPrice;
    private String title;
    private String operatorName;
    private String remindTime;
    private String background;
    private int eventStatus;
    private int isRemind;
    private int isShare;
    private int id;
    private int operatorId;
    private String endDateStr;
    private String startDate;
    private String createDate;
    private List<CouponModel> couponList;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPopoutImage() {
        return popoutImage;
    }

    public void setPopoutImage(String popoutImage) {
        this.popoutImage = popoutImage;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public int getRemindScheme() {
        return remindScheme;
    }

    public void setRemindScheme(int remindScheme) {
        this.remindScheme = remindScheme;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getEventCouponTotalPrice() {
        return eventCouponTotalPrice;
    }

    public void setEventCouponTotalPrice(int eventCouponTotalPrice) {
        this.eventCouponTotalPrice = eventCouponTotalPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(int eventStatus) {
        this.eventStatus = eventStatus;
    }

    public int getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(int isRemind) {
        this.isRemind = isRemind;
    }

    public int getIsShare() {
        return isShare;
    }

    public void setIsShare(int isShare) {
        this.isShare = isShare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<CouponModel> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponModel> couponList) {
        this.couponList = couponList;
    }


}
