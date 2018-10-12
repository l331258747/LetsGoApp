package com.njz.letsgoapp.bean.send;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public class SendNotifyMainModel {
    String msgBroad;
    String read;
    int page;
    int limit;
    String sidx;
    String order;

    public String getMsgBroad() {
        return msgBroad;
    }

    public void setMsgBroad(String msgBroad) {
        this.msgBroad = msgBroad;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
