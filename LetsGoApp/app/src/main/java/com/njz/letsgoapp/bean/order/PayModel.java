package com.njz.letsgoapp.bean.order;

/**
 * Created by LGQ
 * Time: 2018/9/20
 * Function:
 */

public class PayModel {


    /**
     * lastPayTime : 2018-09-21 16:01:32
     * totalAmount : 224
     * subject : 长沙lm导游为您服务！
     * outTradeNo : CS201809201601327810
     * body :
     */

    private String lastPayTime;
    private float totalAmount;
    private String subject;
    private String outTradeNo;
    private String body;

    public String getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(String lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
